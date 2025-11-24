.data
N: .word 16
arr: .word 12, 5, 8, 20, 3, 15, 7, 1, 14, 6, 10, 4, 11, 2, 9, 13
msg: .string "The sorted result is: "
space: .byte ' '
nl: .byte '\n'

.text
.globl main
main:
    la s0, arr
    lw s1, N

    li t0, 0

outer_loop:
    addi t1, s1, -1
    bge t0, t1, sorting_done

    mv t2, t0
    addi t3, t0, 1

inner_loop:
    bge t3, s1, inner_done

    slli t5, t3, 2
    add t6, s0, t5
    lw t4, 0(t6)

    slli t5, t2, 2
    add t6, s0, t5
    lw t5, 0(t6)

    bge t4, t5, skip_update
    mv t2, t3

skip_update:
    addi t3, t3, 1
    j inner_loop

inner_done:
    beq t2, t0, no_swap

    slli t5, t0, 2
    add t3, s0, t5
    lw t6, 0(t3)

    slli t5, t2, 2
    add t4, s0, t5
    lw t5, 0(t4)

    sw t5, 0(t3)
    sw t6, 0(t4)

no_swap:
    addi t0, t0, 1
    j outer_loop

sorting_done:
    li a0, 4
    la a1, msg
    ecall

    li t0, 0

print_loop:
    bge t0, s1, print_done

    slli t5, t0, 2
    add t6, s0, t5
    lw a1, 0(t6)

    li a0, 1
    ecall

    li a0, 11
    lb a1, space
    ecall

    addi t0, t0, 1
    j print_loop

print_done:
    li a0, 11
    lb a1, nl
    ecall

    li a0, 10
    ecall
