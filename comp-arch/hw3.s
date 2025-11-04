.data
prompt: .string "Enter N [0, 9]: "
again: .string "Entered input must be in [0, 9]. Try again.\n"
result: .string "Fibonacci: "
space: .byte ' '
nl: .byte '\n'
fib: .space 40

.text
.globl main
main:
    li a0, 4
    la a1, prompt
    ecall

    li a0, 0x130
    ecall

    li t1, 0x1
    li t2, 0x2
    li a1, 0x0

read_loop:
    li a0, 0x131
    ecall
    beq a0, t1, read_loop
    beq a0, t2, read_loop
    mv t0, a1

    li t3, '0'
    li t4, '9'
    blt t0, t3, invalid
    bgt t0, t4, invalid
    j gen_prepare

invalid:
    li a0, 4
    la a1, again
    ecall
    j main

gen_prepare:
    addi t0, t0, -48
    la s0, fib

    li t5, 1
    sw t5, 0(s0)

    beq t0, x0, print_all

    li t6, 1
    sw t6, 4(s0)

    li s1, 1
    li s2, 1
    li s3, 2
    addi s4, t0, 1

gen_loop:
    add t4, s1, s2

    slli t1, s3, 2
    add t2, s0, t1
    sw t4, 0(t2)

    mv s2, s1
    mv s1, t4

    addi s3, s3, 1
    blt s3, s4, gen_loop

print_all:
    li a0, 4
    la a1, result
    ecall

    li s3, 0
    addi s4, t0, 1

print_loop:
    slli t1, s3, 2
    add t2, s0, t1
    lw a1, 0(t2)
    li a0, 1
    ecall

    li a0, 11
    lb a1, space
    ecall

    addi s3, s3, 1
    blt s3, s4, print_loop

    li a0, 11
    lb a1, nl
    ecall

    li a0, 10
    ecall
