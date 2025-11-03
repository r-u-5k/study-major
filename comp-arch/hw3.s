    .data
promptN: .string "Enter N [0, 9]: "
again: .string "Entered input must be in [0, 9]. Try again.\n"
title: .string "Fibonacci: "
space: .byte   ' '
nl: .byte   '\n'

# 최대 N=9 이므로 f0..f9 총 10개(각 4바이트 = 40바이트) 공간 확보
fib: .space  40

.text
.globl main
main:
# ----- 프롬프트 출력 -----
    li   a0, 4
    la   a1, promptN
    ecall

# ----- (템플릿과 동일한) 문자 입력 준비 -----
    li   a0, 0x130
    ecall

    li   t1, 0x1          # 특수키(예: shift 등) 무시용
    li   t2, 0x2
    li   a1, 0x0          # 최근 키 값이 여기로 들어옴(템플릿과 동일 가정)

# ----- 실제 한 글자 읽기 & 유효성 검사 -----
read_loop:
    li   a0, 0x131
    ecall
    beq  a0, t1, read_loop
    beq  a0, t2, read_loop
    mv   t0, a1           # t0 <- 입력 문자(ASCII)

    li   t3, '0'
    li   t4, '9'
    blt  t0, t3, invalid
    bgt  t0, t4, invalid
    j    gen_prepare

invalid:
    li   a0, 4
    la   a1, again
    ecall
    j    main

# ===== 피보나치 생성 (메모리에 저장) =====
gen_prepare:
    addi t0, t0, -48      # t0 = N (정수)
    la   s0, fib          # s0 = 배열 베이스 주소

    li   t5, 1            # f0 = 1
    sw   t5, 0(s0)

    beq  t0, x0, print_all      # N==0 이면 f0만 저장하고 출력으로

    li   t6, 1            # f1 = 1
    sw   t6, 4(s0)

    li   t7, 1            # prev = f1(루프에서 prev2와 교대)
    li   t8, 1            # prev2 = f0
    li   t3, 2            # i = 2 (다음에 저장할 인덱스)
    addi t9, t0, 1        # limit = N+1  (i < N+1 동안 반복)

gen_loop:
    # f = prev + prev2
    add  t4, t7, t8

    # 주소 = base + (i<<2)
    slli t1, t3, 2
    add  t2, s0, t1
    sw   t4, 0(t2)        # fib[i] = f

    # 다음 단계 준비: prev2 <- prev, prev <- f
    mv   t8, t7
    mv   t7, t4

    addi t3, t3, 1        # i++
    blt  t3, t9, gen_loop # i < N+1 이면 계속

# ===== 메모리에서 읽어 출력 =====
print_all:
    li   a0, 4
    la   a1, title
    ecall

    li   t3, 0            # i = 0
    addi t9, t0, 1        # count = N+1

print_loop:
    slli t1, t3, 2        # offset = i*4
    add  t2, s0, t1
    lw   a1, 0(t2)        # a1 <- fib[i]
    li   a0, 1            # print integer
    ecall

    # 공백 출력
    li   a0, 11
    lb   a1, space
    ecall

    addi t3, t3, 1
    blt  t3, t9, print_loop

    # 줄바꿈
    li   a0, 11
    lb   a1, nl
    ecall

    # 종료
    li   a0, 10
    ecall
