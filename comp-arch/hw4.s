.data
N: .word 16
arr: .word 12, 5, 8, 20, 3, 15, 7, 1, 14, 6, 10, 4, 11, 2, 9, 13
msg: .string "The sorted result is: "
space: .byte ' '
nl: .byte '\n'

.text
.globl main
main:
    # s0 = &arr, s1 = N
    la s0, arr  # 배열 시작 주소
    lw s1, N  # 원소 개수 N

    # 선택 정렬: for (i = 0; i < N-1; i++)
    li t0, 0  # i = 0

outer_loop:
    addi t1, s1, -1  # t1 = N-1
    bge t0, t1, sorting_done  # if (i >= N-1) 종료

    mv t2, t0  # min_idx = i
    addi t3, t0, 1  # j = i + 1

inner_loop:
    bge t3, s1, inner_done  # if (j >= N) 내부 루프 종료

    # val_j = arr[j]
    slli t5, t3, 2  # t5 = j * 4
    add t6, s0, t5  # t6 = &arr[j]
    lw t4, 0(t6)  # t4 = arr[j]

    # val_min = arr[min_idx]
    slli t5, t2, 2  # t5 = min_idx * 4
    add t6, s0, t5  # t6 = &arr[min_idx]
    lw t5, 0(t6)  # t5 = arr[min_idx]

    # if (arr[j] < arr[min_idx]) min_idx = j
    bge t4, t5, skip_update
    mv t2, t3  # min_idx = j

skip_update:
    addi t3, t3, 1  # j++
    j inner_loop

inner_done:
    # if (min_idx != i) swap(arr[i], arr[min_idx])
    beq t2, t0, no_swap

    # addr_i = &arr[i]
    slli t5, t0, 2
    add t3, s0, t5  # t3 = &arr[i]
    lw t6, 0(t3)  # t6 = temp = arr[i]

    # addr_min = &arr[min_idx]
    slli t5, t2, 2
    add t4, s0, t5  # t4 = &arr[min_idx]
    lw t5, 0(t4)  # t5 = arr[min_idx]

    sw t5, 0(t3)  # arr[i] = arr[min_idx]
    sw t6, 0(t4)  # arr[min_idx] = temp

no_swap:
    addi t0, t0, 1  # i++
    j outer_loop

sorting_done:
    li a0, 4
    la a1, msg
    ecall

    li t0, 0  # i = 0

print_loop:
    bge t0, s1, print_done  # if (i >= N) 출력 종료

    slli t5, t0, 2  # t5 = i * 4
    add t6, s0, t5  # t6 = &arr[i]
    lw a1, 0(t6)  # a1 = arr[i]

    li a0, 1  # 정수 출력
    ecall

    li a0, 11  # 공백 출력
    lb a1, space
    ecall

    addi t0, t0, 1  # i++
    j print_loop

print_done:
    li a0, 11
    lb a1, nl
    ecall

    li a0, 10
    ecall
