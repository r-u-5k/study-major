import matplotlib.pyplot as plt
import numpy as np

# 비트가 1이면 High에서 Low로 전환, 0이면 Low에서 High로 전환
def manchester_encoding(data):
    signal = []
    for bit in data:
        if bit == 1:
            signal.extend([1, 0])
        else:
            signal.extend([0, 1])
    return signal

# 비트가 1일 경우 신호 전환 X, 0일 경우 전환
def differential_manchester_encoding(data):
    signal = []
    current_signal = 1
    for bit in data:
        if bit == 1:
            signal.extend([current_signal, current_signal ^ 1])
        else:
            current_signal ^= 1  # 신호 전환
            signal.extend([current_signal, current_signal ^ 1])
    return signal

# 입력 데이터
input_data = [1, 0, 1, 1, 0, 0, 1]

# 인코딩 신호 생성
manchester_signal = manchester_encoding(input_data)
differential_manchester_signal = differential_manchester_encoding(input_data)

# 시간 축 생성
time = np.arange(0, len(manchester_signal))

# 그래프 출력
plt.figure(figsize=(12, 6))

# Manchester
plt.subplot(2, 1, 1)
plt.step(time, manchester_signal, where='mid')
plt.ylim([-0.5, 1.5])
plt.title('Manchester Encoding')
plt.xlabel('Time')
plt.ylabel('Signal')
plt.grid(True)

# Differential Manchester
plt.subplot(2, 1, 2)
plt.step(time, differential_manchester_signal, where='mid')
plt.ylim([-0.5, 1.5])
plt.title('Differential Manchester Encoding')
plt.xlabel('Time')
plt.ylabel('Signal')
plt.grid(True)

plt.tight_layout()
plt.show()
