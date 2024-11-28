2024-2 소프트웨어공학 3팀 Homework#3 - C program Implementation

민상연  202012348   스마트ICT융합공학과
구진    202111731   행정학과
장유진  202011024   전기전자공학부
조규빈  202012362   스마트ICT융합공학과

1. C 버전
    __STDC_VERSION__ = 201112 (gnu11)

2. Compiler 정보
    gcc.exe (MinGW.org GCC-6.3.0-1) 6.3.0
    Copyright (C) 2016 Free Software Foundation, Inc.
    This is free software; see the source for copying conditions.  There is NO
    warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

3. 각 파일 세부 설명
    [main.c]
        RVC Controller와 내부 동작을 정의한다.
        해당 파일은 RVC Controller의 Flow만을 구현하기 위함으로
        SensorInterface들은 모두 rand() 메소드를 통해 임의의 값을
        산출하여 RVC Controller에 전달한다.

    [main.exe]
        Windows 11 환경에서 실행 가능한 main.c의 실행파일이다.

    [main_result.txt]
        main.c의 출력 결과이다.
        기본적으로 사용자가 임의로 HW적인 종료 명령을 내릴 때까지
        main.c의 RVC Controller가 동작하기 때문에
        임의로 Ctrl-Z를 통해 Interrupt한 결과를 삽입하였다.

    [main_simulate.c]
        RVC Controller와 내부 동작을 정의한 main.c에 기반하여
        각 map에 대해 RVC Controller를 Simulate한다.
        RVC의 방향은 "< ^ > v" 로 벽은 '#' Path는 '.' Dust는 숫자로 표기하였다.
        임의로 설정한 각 Tick마다 RVC Controller의 동작과 전체 Map을 출력한다.
        20번의 Tick 이후 해당 Map에 대한 Simulate를 종료한다.

    [main_simulate.exe]
        Windows 11 환경에서 실행 가능한 main_simulate.c의 실행파일이다.
    
    [main_simulate_result.txt]
        main_simulate.c의 출력 결과이다.

    [main_unit_test.c]
        RVC Controller의 메소드들을 Mock하여 가능한
        ObstacleSensor들의 조합에 대한 Unit Test Case를 수행한다.
        Dust의 경우 각 ObstacleSensor들의 조합에 대해 0, 5로 하여
        main()에서서 원본 Controller의 호출을 통해 버퍼에 저장하며 수행한다.

    [main_unit_test.exe]
        Windows 11 환경에서 실행 가능한 main_unit_test.c의 실행파일이다.
    
    [main_unit_test_result.txt]
        main_unit_test_result.c의 출력 결과이다.

    [version.c]
        현재 C 표준의 버전을 출력하는 코드이다.

4. 코드 실행 방법
    # Ubuntu 22.04 LTS
        [main.c]
            $ gcc main.c -o main
            $ ./main

        [main_simulate.c]
            $ gcc main_simulate.c -o main_simulate
            $ ./main_simulate

        [main_unit_test.c]
            $ gcc main_unit_test.c -o main_unit_test
            $ ./main_unit_test
    
    # Windows 11
        [main.c]
            $ gcc main.c -o main.exe
            $ ./main.exe

        [main_simulate.c]
            $ gcc main_simulate.c -o main_simulate.exe
            $ ./main_simulate.exe

        [main_unit_test.c]
            $ gcc main_unit_test.c -o main_unit_test.exe
            $ ./main_unit_test.exe
