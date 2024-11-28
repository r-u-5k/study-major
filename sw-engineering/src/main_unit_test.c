/*
    2024-2 소프트웨어공학 3팀 Homework#3
    C program Implementation

    민상연  202012348   스마트ICT융합공학과
    구진    202111731   행정학과
    장유진  202011024   전기전자공학부
    조규빈  202012362   스마트ICT융합공학과

    [main_unit_test.c]
    RVC Controller의 메소드들을 Mock하여 가능한
    ObstacleSensor들의 조합에 대한 Unit Test Case를 수행한다.
    Dust의 경우 각 ObstacleSensor들의 조합에 대해 0, 5로 하여
    main()에서서 원본 Controller의 호출을 통해 버퍼에 저장하며 수행한다.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 상수 정의
#define FALSE 0
#define TRUE 1
#define OFF 0
#define ON 1
#define DISABLE 0
#define ENABLE 1
#define SINGLE_TICK 500000000

// 원본 main.c의 함수 선언 포함
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

// 테스트를 위한 전역 변수
char MotorCommandBuffer[100];
char CleanerCommandBuffer[100];
char PowerUpCommandBuffer[100];

// Sensor Interface 함수를 Mock하기 위한 전역 변수
unsigned char mockFrontSensor = FALSE;
unsigned char mockLeftSensor = FALSE;
unsigned char mockRightSensor = FALSE;
int mockDustSensor = 0;

// Sensor Interface Mock 함수 구현
unsigned char FrontSensorInterface(void) {
    return mockFrontSensor;
}

unsigned char LeftSensorInterface(void) {
    return mockLeftSensor;
}

unsigned char RightSensorInterface(void) {
    return mockRightSensor;
}

int DustSensorInterface(void) {
    return mockDustSensor;
}

// DetermineObstacleLocation 함수 구현
unsigned char* DetermineObstacleLocation(void) {
    static unsigned char ObstacleLocation[3];
    ObstacleLocation[0] = FrontSensorInterface();
    ObstacleLocation[1] = LeftSensorInterface();
    ObstacleLocation[2] = RightSensorInterface();
    return ObstacleLocation;
}

// DetermineDustExistence 함수 구현
int DetermineDustExistence(void) {
    int DustExistence = DustSensorInterface();
    return DustExistence;
}

// 출력 결과를 캡처하기 위한 인터페이스 함수 수정
void MotorInterface(char * MotorCommand){
    strcpy(MotorCommandBuffer, MotorCommand);
}

void CleanerInterface(unsigned char CleanerCommand) {
    if (CleanerCommand == TRUE)
        strcpy(CleanerCommandBuffer, "Cleaner On");
    else
        strcpy(CleanerCommandBuffer, "Cleaner Off");
}

void PowerUpControllerInterface(unsigned char PowerUpCommand) {
    if (PowerUpCommand == TRUE)
        strcpy(PowerUpCommandBuffer, "PowerUp On");
    else
        strcpy(PowerUpCommandBuffer, "PowerUp Off");
}

// MoveForward 함수 구현
void MoveForward(unsigned char EnableDisable) {
    if(EnableDisable == ENABLE)
        MotorInterface("Move Forward");
    else
        MotorInterface("Stop");
}

// TurnLeft 함수 구현
void TurnLeft(void) {
    MotorInterface("Turn Left");
}

// TurnRight 함수 구현
void TurnRight(void) {
    MotorInterface("Turn Right");
}

// Stop 함수 구현
void Stop(void) {
    MotorInterface("Stop");
}

// Controller
void Controller(unsigned char* ObstacleLocation, int DustExistence) {
    // 1Tick 내의 동작
    // 즉, 연속적이지 않은 경우를 테스트하기 때문에
    // 장애물이 존재할 경우 return
    if (ObstacleLocation[0] == TRUE && ObstacleLocation[1] == FALSE) {
        // Front에만 장애물이 존재할 경우
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        MoveForward(DISABLE);
        TurnLeft();
        return;
    } else if (ObstacleLocation[0] == TRUE && ObstacleLocation[1] == TRUE && ObstacleLocation[2] == FALSE) {
        // Front, Left에 장애물이 존재할 경우
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        MoveForward(DISABLE);
        TurnRight();
        return;
    } else if(ObstacleLocation[0] == TRUE && ObstacleLocation[1] == TRUE && ObstacleLocation[2] == TRUE){
        // Front, Left, Right 모두 장애물이 존재할 경우
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        Stop();
        TurnLeft();
        return;
    }

    MoveForward(ENABLE);
    CleanerInterface(ON);
    if (DustExistence > 0)
        PowerUpControllerInterface(ON);
    else
        PowerUpControllerInterface(OFF);
}

int main(void) {
    // 모든 가능한 Sensor Interface 조합에 대한 테스트 수행
    for (mockFrontSensor = FALSE; mockFrontSensor <= TRUE; mockFrontSensor++) {
        for (mockLeftSensor = FALSE; mockLeftSensor <= TRUE; mockLeftSensor++) {
            for (mockRightSensor = FALSE; mockRightSensor <= TRUE; mockRightSensor++) {
                // DustSensor의 값을 0과 5로 테스트
                for (int dust = 0; dust <= 1; dust++) {
                    mockDustSensor = dust == 0 ? 0 : 5;

                    // ObstacleLocation과 DustExistence 결정
                    unsigned char* ObstacleLocation = DetermineObstacleLocation();
                    int DustExistence = DetermineDustExistence();

                    // 버퍼 초기화
                    strcpy(MotorCommandBuffer, "");
                    strcpy(CleanerCommandBuffer, "");
                    strcpy(PowerUpCommandBuffer, "");

                    // Controller 호출
                    Controller(ObstacleLocation, DustExistence);

                    // 테스트 결과 출력
                    printf("Test Case: Front=%s, Left=%s, Right=%s, Dust=%d\n",
                        mockFrontSensor ? "TRUE" : "FALSE",
                        mockLeftSensor ? "TRUE" : "FALSE",
                        mockRightSensor ? "TRUE" : "FALSE",
                        mockDustSensor);
                    printf("Motor Command: %s\n", MotorCommandBuffer);
                    printf("Cleaner Command: %s\n", CleanerCommandBuffer);
                    printf("PowerUp Command: %s\n", PowerUpCommandBuffer);
                    printf("-------------------------------\n");
                }
            }
        }
    }

    return 0;
}