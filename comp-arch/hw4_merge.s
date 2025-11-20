.data
N: .word 16
arr: .word 12, 5, 8, 20, 3, 15, 7, 1, 14, 6, 10, 4, 11, 2, 9, 13
tmp: .space 64
msg: .string "The sorted result is: "
space: .byte ' '
nl: .byte '\n'

.text
.globl main
main:
    # s1 = N, s2 = arr base, s3 = tmp base
    la s0, N
    lw s1, 0(s0)      # s1 = N
    la s2, arr        # s2 = &arr[0]
    la s3, tmp        # s3 = &tmp[0]

    li s4, 1          # s4 = width = 1

outer_loop:
    # while (width < N)
    bge s4, s1, done_sort

    li s5, 0          # s5 = left = 0

merge_pass_loop:
    # if (left >= N) -> 한 패스 종료
    bge s5, s1, end_merge_pass

    # mid = left + width
    add t0, s5, s4
    # if (mid > N) mid = N
    blt t0, s1, mid_ok
    mv t0, s1
mid_ok:
    mv s6, t0         # s6 = mid

    # right = left + 2*width
    slli t1, s4, 1      # t1 = 2*width
    add t1, s5, t1
    # if (right > N) right = N
    blt t1, s1, right_ok
    mv t1, s1
right_ok:
    mv s7, t1         # s7 = right

    # i = left (t2), j = mid (t3), k = left (t4)
    mv t2, s5         # i
    mv t3, s6         # j
    mv t4, s5         # k

merge_loop:
    # if (i >= mid)  -> 남은 오른쪽만 복사
    bge t2, s6, copy_right
    # if (j >= right)-> 남은 왼쪽만 복사
    bge t3, s7, copy_left

    # t5 = arr[i]
    slli a0, t2, 2      # a0 = i * 4
    add a0, s2, a0
    lw t5, 0(a0)

    # t6 = arr[j]
    slli a1, t3, 2      # a1 = j * 4
    add a1, s2, a1
    lw t6, 0(a1)

    # if (t5 <= t6) tmp[k] = t5; i++;
    # else         tmp[k] = t6; j++;
    ble t5, t6, take_left

    # take right: tmp[k] = t6
    slli a0, t4, 2      # a0 = k * 4
    add a0, s3, a0
    sw t6, 0(a0)
    addi t3, t3, 1      # j++
    addi t4, t4, 1      # k++
    j merge_loop

take_left:
    # tmp[k] = t5
    slli a0, t4, 2
    add a0, s3, a0
    sw t5, 0(a0)
    addi t2, t2, 1      # i++
    addi t4, t4, 1      # k++
    j merge_loop

copy_left:
    # while (i < mid) tmp[k++] = arr[i++]
    bge t2, s6, copy_done
    slli a0, t2, 2
    add a0, s2, a0
    lw t5, 0(a0)      # t5 = arr[i]

    slli a0, t4, 2
    add a0, s3, a0
    sw t5, 0(a0)      # tmp[k] = t5

    addi t2, t2, 1      # i++
    addi t4, t4, 1      # k++
    j copy_left

copy_right:
    # while (j < right) tmp[k++] = arr[j++]
    bge t3, s7, copy_done
    slli a0, t3, 2
    add a0, s2, a0
    lw t5, 0(a0)      # t5 = arr[j]

    slli a0, t4, 2
    add a0, s3, a0
    sw t5, 0(a0)      # tmp[k] = t5

    addi t3, t3, 1      # j++
    addi t4, t4, 1      # k++
    j copy_right

copy_done:
    # tmp[left:right] -> arr[left:right] 로 복사
    mv t4, s5         # t4 = idx = left

copy_back:
    bge t4, s7, copy_back_done

    slli a0, t4, 2
    add a0, s3, a0
    lw t5, 0(a0)      # t5 = tmp[idx]

    slli a0, t4, 2
    add a0, s2, a0
    sw t5, 0(a0)      # arr[idx] = t5

    addi t4, t4, 1
    j copy_back

copy_back_done:
    # left += 2*width
    slli t1, s4, 1      # t1 = 2*width
    add s5, s5, t1
    j merge_pass_loop

end_merge_pass:
    # width *= 2
    slli s4, s4, 1
    j outer_loop

done_sort:
    # 정렬 결과 출력 (Fibonacci 코드와 같은 ecall 규약 사용)
    li a0, 4
    la a1, msg
    ecall

    li t0, 0          # t0 = index = 0

print_loop:
    bge t0, s1, print_end

    slli t1, t0, 2      # t1 = index * 4
    add t2, s2, t1     # &arr[index]
    lw a1, 0(t2)      # a1 = arr[index]
    li a0, 1          # print int
    ecall

    li a0, 11         # print char
    lb a1, space
    ecall

    addi t0, t0, 1
    j print_loop

print_end:
    li a0, 11
    lb a1, nl
    ecall

    li a0, 10
    ecall
