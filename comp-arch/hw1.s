.data
prompt: .string "Enter a lowercase letter (a-z): "
again: .string "Entered character must be in (a-z). Try again.\n"
ok: .string "Next letter: "
nl: .string "\n"

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

loop:
    li a0, 0x131
    ecall
    beq a0, t1, loop
    beq a0, t2, loop
    mv t0, a1

    li t3, 'a'
    li t4, 'z'
    blt t0, t3, invalid
    bgt t0, t4, invalid
    j valid

invalid:
    li a0, 4
    la a1, again
    ecall
    j main

valid:
    addi t0, t0, 1
    ble t0, t4, print_out
    li t0, 'a'

print_out:
    li a0, 4
    la a1, ok
    ecall

    li a0, 11
    mv a1, t0
    ecall

    li a0, 4
    la a1, nl
    ecall

    li a0, 10
    ecall