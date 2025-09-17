    .data
hello:
    .string "Hello World!\n"
    .text
main:
# print_string("Hello World!\n")
    li      a0, 4
    la      a1, hello
    ecall
    li      s0, 0
    li      s1, 100
loop:
# print_int(s0)
    li      a0, 1
    mv      a1, s0
    ecall
# in case of s0 == s1, exit
    beq     s0, s1, exit
# print_char(',')
    li      a0, 11
    li      a1, ','
    ecall
    addi    s0, s0, 1
    j       loop
exit:
    li      a0, 11
    li      a1, '\n'
    ecall
    li      a0, 10           # exit
    ecall