#ifndef MAIN_H
#define MAIN_H

// 상수 정의
#define FALSE 0
#define TRUE 1
#define OFF 0
#define ON 1
#define DISABLE 0
#define ENABLE 1

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

#endif
