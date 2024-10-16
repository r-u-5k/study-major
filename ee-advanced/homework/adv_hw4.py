import os
import random

import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import cross_val_score, TimeSeriesSplit, GridSearchCV

import params as pa

pd.set_option("display.width", 5000)
pd.set_option("display.max_rows", 5000)
pd.set_option("display.max_columns", 5000)


def solar_read():
    file_path = os.path.join(pa.Loc, pa.FileNameSolar)
    solar = pd.read_csv(file_path)
    solar["DeliveryDT"] = pd.to_datetime(solar["DeliveryDT"])
    solar = solar.sort_values(by="DeliveryDT", ascending=[True])
    solar = solar.rename(columns={'MW': "Target"})
    return solar


def weather_read():
    file_path = pa.Loc
    weather_list = []
    file_list = os.listdir(file_path)
    for file in file_list:
        if file.startswith("Weather"):
            full_file_name = os.path.join(file_path, file)
            Weather = pd.read_csv(full_file_name)
            weather_list.append(Weather)

    weather_df = pd.concat(weather_list, ignore_index=True)
    weather_df["DeliveryDT"] = pd.to_datetime(weather_df["DeliveryDT"])
    del weather_df["WeatherType"]
    weather_df = weather_df.sort_values(by="DeliveryDT", ascending=[True])
    return weather_df


def regression():
    return RandomForestRegressor(
        n_estimators=pa.RF_Tree,
        criterion="squared_error",
        max_depth=pa.RF_Depth,
        min_samples_split=pa.RF_MinLeaf * 2,
        min_samples_leaf=pa.RF_MinLeaf,
        n_jobs=pa.RF_njob,
        max_features=pa.RFMF,
        max_samples=pa.max_samples,
        bootstrap=True,
        random_state=random.randint(0, 1000),
        verbose=0,
    )


def training(training_data):
    X_train = training_data.drop(['Target', 'DeliveryDT', 'WeatherStationId'], axis=1)
    y_train = training_data["Target"].copy()

    machine = regression()
    machine.fit(X_train, y_train)

    return machine


def cross_validation(training_data):
    X_train = training_data.drop(['Target', 'DeliveryDT', 'WeatherStationId'], axis=1)
    y_train = training_data["Target"].copy()

    machine = regression()
    scores = cross_val_score(machine, X_train, y_train, cv=TimeSeriesSplit(n_splits=5), scoring='r2')
    accuracy = scores.mean() * 100
    return accuracy


def grid_search(training_data):
    X_train = training_data.drop(['Target', 'DeliveryDT', 'WeatherStationId'], axis=1)
    y_train = training_data["Target"].copy()

    machine = regression()
    param_grid = {
        'n_estimators': [pa.RF_Tree, pa.RF_Tree * 2, pa.RF_Tree * 4],
        'max_depth': [pa.RF_Depth, pa.RF_Depth * 2, pa.RF_Depth * 4],
    }

    grid_search = GridSearchCV(estimator=machine,
                               param_grid=param_grid,
                               cv=TimeSeriesSplit(n_splits=5),
                               scoring='neg_mean_absolute_error')

    grid_search.fit(X_train, y_train)

    best_model = grid_search.best_estimator_
    return best_model


def performance(pred, actual):
    error = np.abs(np.divide(pred - actual, actual, out=np.zeros_like(actual), where=actual != 0)) * 100

    small = np.where(actual < 1000)[0]
    error[small] = 0

    no_zero = np.where(error > 0)[0]
    real_error = error[no_zero]

    accuracy = 100 - np.mean(real_error)
    return accuracy


if __name__ == "__main__":
    training_start = pd.Timestamp(year=2022, month=1, day=1, hour=0)
    training_end = pd.Timestamp(year=2022, month=12, day=31, hour=23)
    testing_start = pd.Timestamp(year=2023, month=1, day=1, hour=0)
    testing_end = pd.Timestamp(year=2023, month=6, day=30, hour=23)

    solar = solar_read()
    weather = weather_read()
    total_data = pd.merge(solar, weather, how='inner', on='DeliveryDT')
    training_data = total_data[(total_data['DeliveryDT'] >= training_start) & (total_data['DeliveryDT'] <= training_end)]
    testing_data = total_data[(total_data['DeliveryDT'] >= testing_start) & (total_data['DeliveryDT'] <= testing_end)]

    # Default
    X_train = training_data.drop(['Target', 'DeliveryDT', 'WeatherStationId'], axis=1)
    y_train = training_data['Target']
    X_test = testing_data.drop(['Target', 'DeliveryDT', 'WeatherStationId'], axis=1)
    y_test = testing_data['Target'].copy()

    fit_machine = training(training_data)
    forecast_train = fit_machine.predict(X_train)
    forecast_train = np.round(forecast_train, 2)
    training_accuracy = performance(forecast_train, y_train.to_numpy())
    print(f"Training Accuracy: {training_accuracy:.2f}%")

    forecast_test = fit_machine.predict(X_test)
    forecast_test = np.round(forecast_test, 2)
    testing_accuracy = performance(forecast_test, y_test.to_numpy())
    print(f"Testing Accuracy: {testing_accuracy:.2f}%")

    # Cross Validation
    print(f"Training Accuracy (Cross Validation): {cross_validation(training_data):.2f}%")
    print(f"Testing Accuracy (Cross Validation): {cross_validation(testing_data):.2f}%")

    # Grid Search
    fit_machine_grid = grid_search(training_data)
    forecast_train_grid = fit_machine_grid.predict(X_train)
    forecast_train_grid = np.round(forecast_train_grid, 2)
    training_accuracy_grid = performance(forecast_train_grid, y_train.to_numpy())
    print(f"Training Accuracy (Grid Search): {training_accuracy_grid:.2f}%")

    forecast_test_grid = fit_machine_grid.predict(X_test)
    forecast_test_grid = np.round(forecast_test_grid, 2)
    testing_accuracy_grid = performance(forecast_test_grid, y_test.to_numpy())
    print(f"Testing Accuracy (Grid Search): {testing_accuracy_grid:.2f}%")
