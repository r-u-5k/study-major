.data
prompt1: .string "Enter the first input in [0, 9]: "
prompt2: .string "Enter the second input in [0, 9]: "
again: .string "Entered input must be in [0, 9]. Try again.\n"
result: .string "The multiplication result is: "

.text
.globl main
main:
    li a0, 4
    la a1, prompt1
    ecall

    li a0, 0x130
    ecall

    li t1, 0x1
    li t2, 0x2
    li a1, 0x0

loop:
    li a0, 0x131
    ecall

    beq a0, t1, loop
    beq a0, t2, loop
    mv t0, a1

    li t3, '0'
    li t4, '9'
    blt t0, t3, invalid
    bgt t0, t4, invalid
    j main2

invalid:
    li a0, 4
    la a1, again
    ecall
    j main

main2:
    li a0, 4
    la a1, prompt2
    ecall

    li a0, 0x130
    li a1, 0x0
    ecall

loop2:
    li a0, 0x131
    ecall

    beq a0, t1, loop2
    beq a0, t2, loop2
    mv t5, a1

    blt t5, t3, invalid2
    bgt t5, t4, invalid2
    j calc

invalid2:
    li a0, 4
    la a1, again
    ecall
    j main2

calc:
    addi t0, t0, -48
    addi t5, t5, -48
    li t6, 0

mul_loop:
    beq t5, x0, print_out
    add t6, t6, t0
    addi t5, t5, -1
    j mul_loop

print_out:
    li a0, 4
    la a1, result
    ecall

    li a0, 1
    mv a1, t6
    ecall

    li a0, 11
    li a1, '\n'
    ecall

    li a0, 10
    ecall