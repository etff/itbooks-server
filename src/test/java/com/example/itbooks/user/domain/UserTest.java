package com.example.itbooks.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("사용자 정보를 수정한다")
    @Test
    void changeWith() {
        User user = User.builder()
                .name("USER1")
                .build();

        user.changeWith("USER2");

        assertThat(user.getName()).isEqualTo("USER2");
    }


    @DisplayName("사용자의 정보를 삭제 시나리오")
    @TestFactory
    Collection<DynamicTest> delete() {
        User user = User.builder().build();

        return Arrays.asList(
                DynamicTest.dynamicTest("사용자 생성시 지워지지않은 상태이다", () -> {
                    assertThat(user.isDeleted()).isFalse();
                }),
                DynamicTest.dynamicTest("사용자 삭제를 하면 지워진 상태가 된다.", () -> {
                    user.destroy();
                    assertThat(user.isDeleted()).isTrue();
                })
        );
    }
}
