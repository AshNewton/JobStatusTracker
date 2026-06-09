package com.example.jobstatustracker.data

enum class ActivityType(val displayName: String) {
    APPLIED("Resume submitted"),
    INTERVIEW("Interview"),
    REJECTED("Rejected"),
    HIRED("Hired"),
    TEST("Test"),
}