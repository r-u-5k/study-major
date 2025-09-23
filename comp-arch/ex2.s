.data
prompt: .string "Enter a char: "
ok: .string "Enetered char: "
nl: .string "\n"

.text
.globl  main
main:
# 0) Print prompt (this text may get mirrored into the input buffer…)
    li a0, 4
    la a1, prompt
    ecall

# 1) Activate input ONCE (do this BEFORE printing)
    li a0, 0x130
    ecall
# 2) DRAIN any pending chars that came from the prompt echo
    li t1, 0x1              # read state (waiting input)
    li t2, 0x2              # read state (draining prompt)
    li a1, 0x0
drain_prompt:
    li a0, 0x131
    ecall                        # a0=state, a1=utf16 when a0==2
    beq a0, t1, drain_prompt # no char input
    beq a0, t2, drain_prompt # while got char → discard and keep draining

    mv t0, a1
    
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