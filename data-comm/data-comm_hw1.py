import matplotlib.pyplot as plt
import numpy as np


# High에서 Low로 전환되면 0, Low에서 High로 전환되면 1
def manchester_encoding(data):
    encoded_signal = [1]
    for bit in data:
        if bit == 1:
            encoded_signal.extend([0, 1])
        else:
            encoded_signal.extend([1, 0])
    print("Manchester: " + str(encoded_signal))
    return encoded_signal


# 비트의 시작에서 신호 전환이 발생하면 0, 신호 전환이 발생하지 않으면 1
def differential_manchester_encoding(data):
    encoded_signal = [1, 0]
    for bit in data[1:]:
        if bit == 1:
            encoded_signal.extend([encoded_signal[-1], encoded_signal[-1] ^ 1])
        elif bit == 0:
            encoded_signal.extend([encoded_signal[-1] ^ 1, encoded_signal[-1]])
    print("Differential Manchester: " + str(encoded_signal))
    return encoded_signal


input_data = [0, 1, 0, 0, 1, 1, 0, 1, 0, 0]

manchester_signal = manchester_encoding(input_data)
differential_manchester_signal = differential_manchester_encoding(input_data)

plt.figure(figsize=(12, 6))

# Manchester Encoding
plt.subplot(2, 1, 1)
plt.step(np.arange(0, len(manchester_signal)), manchester_signal, where='pre')
plt.ylim([-0.5, 1.5])
plt.title('Manchester Encoding')
plt.xlabel('Time')
plt.ylabel('Signal')
plt.grid(True)

# Differential Manchester Encoding
plt.subplot(2, 1, 2)
plt.step(np.arange(0, len(differential_manchester_signal)), differential_manchester_signal, where='post')
plt.ylim([-0.5, 1.5])
plt.title('Differential Manchester Encoding')
plt.xlabel('Time')
plt.ylabel('Signal')
plt.grid(True)

plt.tight_layout()
plt.show()
