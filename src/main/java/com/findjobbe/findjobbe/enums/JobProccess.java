package com.findjobbe.findjobbe.enums;

public enum JobProccess {
    APPLICATION_SUBMITTED,   // Ứng viên đã nộp đơn
    APPLICATION_REVIEW,      // Nhà tuyển dụng đang xem xét hồ sơ
    SCREENING,              // Sàng lọc hồ sơ ban đầu
    PHONE_INTERVIEW,        // Phỏng vấn qua điện thoại
    TECHNICAL_TEST,         // Kiểm tra kỹ thuật (coding test, bài tập, ...)
    FIRST_INTERVIEW,        // Vòng phỏng vấn đầu tiên
    SECOND_INTERVIEW,       // Vòng phỏng vấn thứ hai
    FINAL_INTERVIEW,        // Vòng phỏng vấn cuối cùng
    OFFER_NEGOTIATION,      // Đàm phán lương và điều kiện hợp đồng
    OFFER_SENT,             // Đã gửi offer
    OFFER_ACCEPTED,         // Ứng viên đã chấp nhận offer
    HIRED,                  // Đã nhận việc
    ONBOARDING,             // Đang trong quá trình onboard
    REJECTED,               // Ứng viên bị từ chối
    WITHDRAWN               // Ứng viên rút đơn
}
