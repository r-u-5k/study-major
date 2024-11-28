#include "unity.h"
#include "../src/main.h" // 헤더 파일 참조

void setUp(void) {
    // 테스트 전 초기화 코드
}

void tearDown(void) {
    // 테스트 후 정리 코드
}

void test_FrontSensorInterface(void) {
    int result = FrontSensorInterface();
    TEST_ASSERT_TRUE(result == TRUE || result == FALSE);
}

void test_DetermineObstacleLocation(void) {
    unsigned char* obstacleLocation = DetermineObstacleLocation();
    TEST_ASSERT_NOT_NULL(obstacleLocation);
    TEST_ASSERT_TRUE(obstacleLocation[0] == TRUE || obstacleLocation[0] == FALSE);
    TEST_ASSERT_TRUE(obstacleLocation[1] == TRUE || obstacleLocation[1] == FALSE);
    TEST_ASSERT_TRUE(obstacleLocation[2] == TRUE || obstacleLocation[2] == FALSE);
}

int main(void) {
    UNITY_BEGIN();
    RUN_TEST(test_FrontSensorInterface);
    RUN_TEST(test_DetermineObstacleLocation);
    return UNITY_END();
}
