import matplotlib.pyplot as plt
import numpy as np


# 각 데이터 비트가 1이면 하이에서 로우로, 0이면 로우에서 하이로 전환
def manchester_encoding(data):
    encoded_signal = []
    for bit in data:
        if bit == 1:
            encoded_signal.extend([1, 0])
        else:
            encoded_signal.extend([0, 1])
    return encoded_signal


# 매 비트 전환이 이전 신호에 따라 다르게 적용됨. 비트가 1일 경우 전환이 없고, 0일 경우 전환이 발생
def differential_manchester_encoding(data):
    encoded_signal = []
    current_signal = 1  # 시작 신호 상태
    for bit in data:
        if bit == 1:
            # 1이면 상태 전환 없음
            encoded_signal.extend([current_signal, current_signal ^ 1])
        else:
            # 0이면 상태 전환
            current_signal ^= 1
            encoded_signal.extend([current_signal, current_signal ^ 1])
    return encoded_signal


# 입력 데이터
data_bits = [1, 0, 1, 1, 0, 0, 1]

# 인코딩된 신호 생성
manchester_signal = manchester_encoding(data_bits)
differential_manchester_signal = differential_manchester_encoding(data_bits)

# 시간 축 생성
time = np.arange(0, len(manchester_signal))

# 그래프 출력
plt.figure(figsize=(10, 2))
plt.step(time, manchester_signal, where='mid')
plt.step(time, differential_manchester_signal, where='mid')
plt.ylim([-0.5, 1.5])
plt.title('Manchester Encoding')
plt.show()
