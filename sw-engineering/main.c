/*
    2024-2 소프트웨어공학 3팀 Homework#3
    C program Implementation

    민상연  202012348   스마트ICT융합공학과
    구진    202111731   행정학과
    장유진  202011024   전기전자공학부
    조규빈  202012362   스마트ICT융합공학과

    [main.c]
    RVC Controller와 내부 동작을 정의한다.
    해당 파일은 RVC Controller의 Flow만을 구현하기 위함으로
    SensorInterface들은 모두 rand() 메소드를 통해 임의의 값을
    산출하여 RVC Controller에 전달한다.
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// 상수 정의
#define FALSE 0
#define TRUE 1
#define OFF 0
#define ON 1
#define DISABLE 0
#define ENABLE 1
#define SINGLE_TICK 500000000

// 임베디드 시스템의 최적화 방지를 위해 volatile 키워드 사용
volatile int tick = 0;
int DigitalClock(void);

// 함수 프로토타입
unsigned char FrontSensorInterface(void);
unsigned char LeftSensorInterface(void);
unsigned char RightSensorInterface(void);
int DustSensorInterface(void);

unsigned char* DetermineObstacleLocation(void);
int DetermineDustExistence(void);

void MoveForward(unsigned char EnableDisable);
void TurnLeft(void);
void TurnRight(void);
void Stop(void);

void MotorInterface(char * MotorCommand);
void CleanerInterface(unsigned char CleanerCommand);
void PowerUpControllerInterface(unsigned char PowerUpCommand);

void Controller(unsigned char* ObstacleLocation, int DustExistence);

// 메인 모듈
int main(void) {
    // 랜덤 시드 설정
    srand(time(NULL));

    printf("[RVC Controller System] Powering up\n");

    while (1) {
        unsigned char* ObstacleLocation;
        ObstacleLocation = DetermineObstacleLocation();

        int DustExistence = DetermineDustExistence();

        Controller(ObstacleLocation, DustExistence);
    }

    return 0;
}

// Digital Clock
int DigitalClock(void){
    // 시뮬레이션을 위해 임의로 Delay 발생
    // 임베디드 시스템의 최적화 방지를 위해 volatile 키워드 사용
    for (volatile int i = 0; i < SINGLE_TICK; ++i);

    tick += 1;
    return tick;
}

// Sensor Interfaces
unsigned char FrontSensorInterface(void) {
    return rand() % 2 == 0 ? FALSE : TRUE;
}

unsigned char LeftSensorInterface(void) {
    return rand() % 2 == 0 ? FALSE : TRUE;
}

unsigned char RightSensorInterface(void) {
    return rand() % 2 == 0 ? FALSE : TRUE;
}

int DustSensorInterface(void) {
    int DustExistence = rand() % 2; // 0 or 1

    if(DustExistence != 0)
        DustExistence = rand() % 11; // 0 to 10
    return DustExistence;
}

// Decision Modules
unsigned char* DetermineObstacleLocation(void) {
    static unsigned char ObstacleLocation[3];
    ObstacleLocation[0] = FrontSensorInterface();
    ObstacleLocation[1] = LeftSensorInterface();
    ObstacleLocation[2] = RightSensorInterface();
    return ObstacleLocation;
}

int DetermineDustExistence(void) {
    int DustExistence = DustSensorInterface();
    return DustExistence;
}

// Actuation Modules
void MoveForward(unsigned char EnableDisable) {
    if(EnableDisable == ENABLE)
        MotorInterface("Move Forward");
    else
        MotorInterface("Stop");
}

void TurnLeft(void) {
    for(int i = 0;i < 5;i++){
        DigitalClock();
        MotorInterface("Turn Left");
    }
}

void TurnRight(void) {
    for(int i = 0;i < 5;i++){
        DigitalClock();
        MotorInterface("Turn Right");
    }
}

void Stop(void) {
    MotorInterface("Stop");
}

void MotorInterface(char * MotorCommand){
    printf("[Motor Command] %s\n", MotorCommand);
}

void CleanerInterface(unsigned char CleanerCommand) {
    if (CleanerCommand == TRUE)
        printf("[Cleaner Command] Cleaner On\n");
    else
        printf("[Cleaner Command] Cleaner Off\n");
}

void PowerUpControllerInterface(unsigned char PowerUpCommand) {
    if (PowerUpCommand == TRUE)
        printf("[PowerUp Command] PowerUp On\n");
    else
        printf("[PowerUp Command] PowerUp Off\n");
}

// Controller
void Controller(unsigned char* ObstacleLocation, int DustExistence) {
    if (ObstacleLocation[0] == TRUE && ObstacleLocation[1] == FALSE) {
        // Front에만 장애물이 존재할 경우
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        MoveForward(DISABLE);
        TurnLeft();
    } else if (ObstacleLocation[0] == TRUE && ObstacleLocation[1] == TRUE && ObstacleLocation[2] == FALSE) {
        // Front, Left에 장애물이 존재할 경우
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        MoveForward(DISABLE);
        TurnRight();
    } else if(ObstacleLocation[0] == TRUE && ObstacleLocation[1] == TRUE && ObstacleLocation[2] == TRUE){
        // Front, Left, Right 모두 장애물이 존재할 경우
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        Stop();
        TurnLeft();
    }

    MoveForward(ENABLE);
    CleanerInterface(ON);
    if (DustExistence > 0)
        PowerUpControllerInterface(ON);
    else
        PowerUpControllerInterface(OFF);
}