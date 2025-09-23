.data
msg: .string "Hello from memory!\n" # example string
nl: .string "\n"
val: .word 12345
ch: .byte 'Z'

.text
.globl  main
main:
# 1) print string
    li a0, 4
    la a1, msg
    ecall
# 2) read 32b int from memory and then print it
    lw t0, val                # t0 (reg) = 12345 (from mem)
    li a0, 1                  # print_int
    mv a1, t0
    ecall
# new line
    li a0, 11
    li a1, '\n'
    ecall
# 3) read 1B char from memory and then print it
    lb t1, ch                 # t1 = 'Z'
    li a0, 11                 # print_char
    mv a1, t1
    ecall
# new line (another way)
    li a0, 4
    la a1, nl
    ecall
# 4) read 32b int from reg and then print it
    li s0, 2025               # s0 (reg) = 2025 (imm)
    li a0, 1
    mv a1, s0
    ecall
# print ","
    li a0, 11
    li a1, ','
    ecall
    li a0, 11
    li a1, ' '
    ecall
# print another value
    li s1, 3402
    li a0, 1
    mv a1, s1
    ecall
# the last new line
    li a0, 4
    la a1, nl
    ecall
# exit
    li a0, 10
    ecall
