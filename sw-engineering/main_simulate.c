/*
    2024-2 소프트웨어공학 3팀 Homework#3
    C program Implementation

    민상연  202012348   스마트ICT융합공학과
    구진    202111731   행정학과
    장유진  202011024   전기전자공학부
    조규빈  202012362   스마트ICT융합공학과

    [main_simulate.c]
    RVC Controller와 내부 동작을 정의한 main.c에 기반하여
    각 map에 대해 RVC Controller를 Simulate한다.
    RVC의 방향은 "< ^ > v" 로 벽은 '#' Path는 '.' Dust는 숫자로 표기하였다.
    임의로 설정한 각 Tick마다 RVC Controller의 동작과 전체 Map을 출력한다.
    20번의 action_count 이후 해당 Map에 대한 Simulate를 종료한다.
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

#define SINGLE_TICK 1000

// Simulate를 위한 상수 정의
#define NORTH 0
#define EAST 1
#define SOUTH 2
#define WEST 3

// Map 정의
// Test Case는 40개로 정의
#define MAP_NUM 40
#define MAX_ROWS 5
#define MAX_COLS 7

int map[MAX_ROWS][MAX_COLS];

// -1: 벽, 0: 경로 (dust 없음), 1~10: Dust 양
int map_data[MAP_NUM][MAX_ROWS][MAX_COLS] = {
    // Map 1
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 2
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 3
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1,  0,  0,  0, -1,  0, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 4
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1, -1, -1,  0,  0,  0, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 5
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 6
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 7
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0,  0, -1, -1},
        {-1,  0, -1, -1,  0,  0, -1},
        {-1, -1,  0,  0,  0, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 8
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 9
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 10
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 11
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0,  0,  0, -1},
        {-1, -1, -1, -1, -1,  0, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 12
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1,  0, -1,  0, -1, -1, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 13
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1, -1,  0, -1},
        {-1, -1,  0,  0,  0, -1, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 14
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1, -1,  0,  0,  0, -1, -1},
        {-1, -1, -1, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 15
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 16
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1,  0, -1, -1},
        {-1,  0,  0,  0,  0, -1, -1},
        {-1, -1, -1, -1,  0, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 17
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0, -1,  0, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1,  0,  0,  0, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 18
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1, -1, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 19
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0, -1, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 20
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1, -1, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 21
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 22
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1,  0,  0,  0, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 23
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1, -1,  0, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 24
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1,  0,  0, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1,  0, -1, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 25
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1,  0,  0,  0, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 26
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 27
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 28
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0,  0, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1,  0,  0, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 29
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 30
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0,  0, -1, -1},
        {-1, -1, -1, -1, -1,  0, -1},
        {-1,  0,  0,  0,  0, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 31
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 32
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0,  0,  0, -1},
        {-1, -1, -1, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 33
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0,  0,  0, -1},
        {-1,  0,  0, -1, -1,  0, -1},
        {-1,  0, -1,  0,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 34
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 35
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0,  0, -1, -1, -1},
        {-1,  0, -1, -1,  0,  0, -1},
        {-1, -1, -1,  0,  0, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 36
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1,  0,  0,  0, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 37
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1, -1,  0, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1,  0,  0, -1, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 38
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1, -1,  0,  0, -1},
        {-1, -1, -1,  0, -1, -1, -1},
        {-1,  0, -1, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 39
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1,  0, -1, -1, -1,  0, -1},
        {-1,  0,  0, -1,  0,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    },
    // Map 40
    {
        {-1, -1, -1, -1, -1, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1,  0, -1,  0, -1, -1},
        {-1,  0, -1,  0, -1,  0, -1},
        {-1, -1, -1, -1, -1, -1, -1}
    }
};

// RVCState 구조체
typedef struct {
    int x;
    int y;
    // 0: North, 1: East, 2: South, 3: West
    int direction;
} RVCState;

// 전역 변수 정의
volatile int tick = 0;
RVCState rvc;

// 함수 프로토타입
void InitializeMap(int index);
void InitializeRVC(void);
void PrintMap(void);
void DigitalClock(void);

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

// Helper 함수
void UpdateRVCPosition(void);

// 메인 모듈
int main(void) {
    for (int i = 0; i < MAP_NUM; i++){
        InitializeMap(i);
        InitializeRVC();

        printf("[RVC Controller System] Powering up\n");

        int action_count = 0;
        while (action_count <= 20) {
            unsigned char* ObstacleLocation = DetermineObstacleLocation();
            int DustExistence = DustSensorInterface();

            // 시뮬레이션 출력을 위해 순서 변경
            PrintMap();
            Controller(ObstacleLocation, DustExistence);

            DigitalClock();
            action_count += 1;
        }
    }
    return 0;
}

void InitializeMap(int index) {
    srand(time(NULL));

    // default map_data를 통해 map 초기화
    for (int i = 0; i < MAX_ROWS; i++)
        for (int j = 0; j < MAX_COLS; j++)
            map[i][j] = map_data[index][i][j];

    // 임의의 테스트 케이스 추가 로직 구현
    // 현재 map에 랜덤하게 먼지 추가
    for (int i = 0; i < MAX_ROWS; i++) {
        for (int j = 0; j < MAX_COLS; j++) {
            if (map[i][j] == 0) {
                if (rand() % 2 == 0) {
                    map[i][j] = rand() % 10 + 1;
                }
            }
        }
    }

    printf("\n################## Successfully Initialized Map %d ##################\n", index + 1);
}

void InitializeRVC(void) {
    // RVC의 위치와 방향 토기화
    rvc.x = 1;
    rvc.y = 1;
    rvc.direction = EAST;
    tick = 0;
}

void PrintMap(void) {
    printf("\n<Tick: %d>\n", tick);
    for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                if (i == rvc.y && j == rvc.x) {
                    // 방향에 따라 map에 RVC 출력
                    char dirChar;
                    switch (rvc.direction) {
                        case NORTH: dirChar = '^'; break;
                        case EAST: dirChar = '>'; break;
                        case SOUTH: dirChar = 'v'; break;
                        case WEST: dirChar = '<'; break;
                        default: dirChar = '?'; break;
                    }
                    printf(" %c ", dirChar);
                } else if (map[i][j] == -1) {
                    printf(" # "); // 벽
                } else if (map[i][j] == 0) {
                    printf(" . "); // 경로
                } else {
                    if(map[i][j] == 10)
                    printf(" %d", map[i][j]);
                    else
                    printf(" %d ", map[i][j]);
                }
            }
            printf("\n");
    }
}

// Digital Clock
void DigitalClock(void){
    // 시뮬레이션을 위해 임의로 Delay 발생
    // 임베디드 시스템의 최적화 방지를 위해 volatile 키워드 사용
    for (volatile int i = 0; i < SINGLE_TICK; ++i);

    tick += 1;
}

// Sensor Interfaces
unsigned char FrontSensorInterface(void) {
    int x = rvc.x;
    int y = rvc.y;

    switch (rvc.direction) {
        case NORTH: y -= 1; break;
        case EAST:  x += 1; break;
        case SOUTH: y += 1; break;
        case WEST:  x -= 1; break;
    }

    if (x < 0 || x >= MAX_COLS || y < 0 || y >= MAX_ROWS)
        return TRUE;

    if (map[y][x] == -1)
        return TRUE;
    else
        return FALSE;
}

unsigned char LeftSensorInterface(void) {
    int x = rvc.x;
    int y = rvc.y;

    // RVC를 왼쪽으로 회전
    int leftDirection = (rvc.direction + 3) % 4;
    switch (leftDirection) {
        case NORTH: y -= 1; break;
        case EAST:  x += 1; break;
        case SOUTH: y += 1; break;
        case WEST:  x -= 1; break;
    }

    if (x < 0 || x >= MAX_COLS || y < 0 || y >= MAX_ROWS)
        return TRUE;

    if (map[y][x] == -1)
        return TRUE;
    else
        return FALSE;
}

unsigned char RightSensorInterface(void) {
    int x = rvc.x;
    int y = rvc.y;

    // RVC를 오른쪽으로 회전
    int rightDirection = (rvc.direction + 1) % 4;
    switch (rightDirection) {
        case NORTH: y -= 1; break;
        case EAST:  x += 1; break;
        case SOUTH: y += 1; break;
        case WEST:  x -= 1; break;
    }

    if (x < 0 || x >= MAX_COLS || y < 0 || y >= MAX_ROWS)
        return TRUE;

    if (map[y][x] == -1)
        return TRUE;
    else
        return FALSE;
}

int DustSensorInterface(void) {
    // 현 위치의 Dust의 양 반환
    return map[rvc.y][rvc.x]; 
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
    if(EnableDisable == ENABLE) {
        MotorInterface("Move Forward");
    } else {
        MotorInterface("Stop");
    }
}

void TurnLeft(void) {
    // FSM에서는 회전하는데 5초가 걸릴 것으로 예상하였기 때문에
    // 임의로 Tick 5 추가
    for(int i = 0;i < 5;i++)
        DigitalClock();

    MotorInterface("Turn Left");
    rvc.direction = (rvc.direction + 3) % 4;
}

void TurnRight(void) {
    // FSM에서는 회전하는데 5초가 걸릴 것으로 예상하였기 때문에
    // 임의로 Tick 5 추가
    for(int i = 0;i < 5;i++)
        DigitalClock();
    
    MotorInterface("Turn Right");
    rvc.direction = (rvc.direction + 1) % 4;
}

void Stop(void) {
    MotorInterface("Stop");
}

void MotorInterface(char * MotorCommand){
    printf("[Motor Command] %s\n", MotorCommand);
}

void CleanerInterface(unsigned char CleanerCommand) {
    if (CleanerCommand == TRUE) {
        printf("[Cleaner Command] Cleaner On\n");
    } else
        printf("[Cleaner Command] Cleaner Off\n");
}

void PowerUpControllerInterface(unsigned char PowerUpCommand) {
    if (PowerUpCommand == TRUE){
        printf("[PowerUp Command] PowerUp On\n");
        map[rvc.y][rvc.x] = 0; // 먼지 제거
        printf("[Action] Cleaned the dust (%d, %d)\n", rvc.x, rvc.y);
    }
    else{
        printf("[PowerUp Command] PowerUp Off\n");
    }
}

// Controller
void Controller(unsigned char* ObstacleLocation, int DustExistence) {
    if (ObstacleLocation[0] == TRUE && ObstacleLocation[1] == FALSE) {
        // Front에만 장애물이 존재할 경우
        printf("[RVC Controller System]-[Warning] Obstacle Detected (F).\n");
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        MoveForward(DISABLE);
        TurnLeft();
    } else if (ObstacleLocation[0] == TRUE && ObstacleLocation[1] == TRUE && ObstacleLocation[2] == FALSE) {
        // Front, Left에 장애물이 존재할 경우
        printf("[RVC Controller System]-[Warning] Obstacle Detected (F && L).\n");
        PowerUpControllerInterface(OFF);
        CleanerInterface(OFF);
        MoveForward(DISABLE);
        TurnRight();
    } else if(ObstacleLocation[0] == TRUE && ObstacleLocation[1] == TRUE && ObstacleLocation[2] == TRUE){
        // Front, Left, Right 모두 장애물이 존재할 경우
        printf("[RVC Controller System]-[Warning] Obstacle Detected (F && L && R).\n");
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

    // MoveForward ENABLE 전환
    // -> 청소 로직 진행
    // -> 시뮬레이션 상의 위치 업데이트
    // 시뮬레이션 환경에서는 현실과 다르게 1 Tick 단위로 동작을 수행
    // 따라서 동작의 연속성을 구현하기 위해 이러한 로직으로 수행
    UpdateRVCPosition();
}

// Helper 함수
void UpdateRVCPosition(void) {
    int x = rvc.x;
    int y = rvc.y;

    // 현 위치에 기반하여 다음 위치 게산
    switch (rvc.direction) {
        case NORTH: y -= 1; break;
        case EAST:  x += 1; break;
        case SOUTH: y += 1; break;
        case WEST:  x -= 1; break;
    }

    // 다음 위치가 Valid한지 검사
    if (x >= 0 && x < MAX_COLS && y >= 0 && y < MAX_ROWS && map[y][x] != -1) {
        rvc.x = x;
        rvc.y = y;
    } else{
        printf("[RVC Controller System]-[Warning] Cannot move forward\n");
    }
}
