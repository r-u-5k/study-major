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
    la s0, N
    lw s1, 0(s0)
    la s2, arr
    la s3, tmp

    li s4, 1

outer_loop:
    bge s4, s1, done_sort

    li s5, 0

merge_pass_loop:
    bge s5, s1, end_merge_pass

    add t0, s5, s4
    blt t0, s1, mid_ok
    mv t0, s1
mid_ok:
    mv s6, t0

    slli t1, s4, 1
    add t1, s5, t1
    blt t1, s1, right_ok
    mv t1, s1
right_ok:
    mv s7, t1

    mv t2, s5
    mv t3, s6
    mv t4, s5

merge_loop:
    bge t2, s6, copy_right
    bge t3, s7, copy_left

    slli a0, t2, 2
    add a0, s2, a0
    lw t5, 0(a0)

    slli a1, t3, 2
    add a1, s2, a1
    lw t6, 0(a1)

    ble t5, t6, take_left

    slli a0, t4, 2
    add a0, s3, a0
    sw t6, 0(a0)
    addi t3, t3, 1
    addi t4, t4, 1
    j merge_loop

take_left:
    slli a0, t4, 2
    add a0, s3, a0
    sw t5, 0(a0)
    addi t2, t2, 1
    addi t4, t4, 1
    j merge_loop

copy_left:
    bge t2, s6, copy_done
    slli a0, t2, 2
    add a0, s2, a0
    lw t5, 0(a0)

    slli a0, t4, 2
    add a0, s3, a0
    sw t5, 0(a0)

    addi t2, t2, 1
    addi t4, t4, 1
    j copy_left

copy_right:
    bge t3, s7, copy_done
    slli a0, t3, 2
    add a0, s2, a0
    lw t5, 0(a0)

    slli a0, t4, 2
    add a0, s3, a0
    sw t5, 0(a0)

    addi t3, t3, 1
    addi t4, t4, 1
    j copy_right

copy_done:
    mv t4, s5

copy_back:
    bge t4, s7, copy_back_done

    slli a0, t4, 2
    add a0, s3, a0
    lw t5, 0(a0)

    slli a0, t4, 2
    add a0, s2, a0
    sw t5, 0(a0)

    addi t4, t4, 1
    j copy_back

copy_back_done:
    slli t1, s4, 1
    add s5, s5, t1
    j merge_pass_loop

end_merge_pass:
    slli s4, s4, 1
    j outer_loop

done_sort:
    li a0, 4
    la a1, msg
    ecall

    li t0, 0

print_loop:
    bge t0, s1, print_end

    slli t1, t0, 2
    add t2, s2, t1
    lw a1, 0(t2)
    li a0, 1
    ecall

    li a0, 11
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
