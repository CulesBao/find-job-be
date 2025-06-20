# 🚀 FindJob Backend API

<div align="center">
  <img src="src/main/resources/static/pl-main-logo.png" alt="FindJob Logo" width="200"/>
  
  [![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)
  [![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://docker.com/)
  [![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
</div>

## 📖 Giới thiệu

**FindJob Backend** là một REST API mạnh mẽ được xây dựng với Spring Boot, phục vụ cho nền tảng tìm kiếm việc làm. Hệ thống cung cấp các tính năng toàn diện cho cả ứng viên và nhà tuyển dụng, bao gồm quản lý hồ sơ, đăng tuyển dụng, ứng tuyển và nhiều tính năng khác.

## ✨ Tính năng chính

### 🔐 Xác thực & Bảo mật

- **JWT Authentication** - Xác thực bảo mật với JSON Web Token
- **OAuth2 Integration** - Đăng nhập với Google
- **Role-based Authorization** - Phân quyền dựa trên vai trò (Candidate/Employer)
- **Password Encryption** - Mã hóa mật khẩu với BCrypt
- **Email Verification** - Xác thực tài khoản qua email

### 👥 Quản lý người dùng

- **Account Management** - Quản lý tài khoản, đổi mật khẩu
- **Profile System** - Hồ sơ chi tiết cho ứng viên và nhà tuyển dụng
- **Social Links** - Liên kết mạng xã hội
- **Avatar/Logo Upload** - Upload ảnh đại diện với Cloudinary

### 💼 Tính năng việc làm

- **Job Posting** - Đăng tin tuyển dụng
- **Job Search & Filter** - Tìm kiếm và lọc việc làm
- **Job Application** - Ứng tuyển với CV và cover letter
- **Application Management** - Quản lý đơn ứng tuyển
- **Application Status Tracking** - Theo dõi trạng thái ứng tuyển

### 🌟 Tính năng xã hội

- **Follow System** - Theo dõi ứng viên/nhà tuyển dụng
- **Chat Integration** - Tích hợp chat AI
- **Geographic Support** - Hỗ trợ tỉnh/thành phố Việt Nam

## 🛠️ Công nghệ sử dụng

| Công nghệ           | Phiên bản | Mục đích                  |
| ------------------- | --------- | ------------------------- |
| **Java**            | 17        | Ngôn ngữ lập trình chính  |
| **Spring Boot**     | 3.4.2     | Framework backend         |
| **Spring Security** | 6.4.4     | Bảo mật và xác thực       |
| **Spring Data JPA** | -         | ORM và database access    |
| **PostgreSQL**      | Latest    | Cơ sở dữ liệu             |
| **JWT**             | 4.4.0     | JSON Web Token            |
| **BCrypt**          | 0.10.2    | Mã hóa mật khẩu           |
| **Cloudinary**      | 1.38.0    | Lưu trữ hình ảnh          |
| **Thymeleaf**       | -         | Template engine cho email |
| **Lombok**          | 1.18.36   | Giảm boilerplate code     |
| **Docker**          | -         | Containerization          |

## 🚀 Cài đặt và chạy dự án

### Yêu cầu hệ thống

- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Docker & Docker Compose (tùy chọn)

### 1. Clone repository

```bash
git clone https://github.com/your-username/find-job-be.git
cd find-job-be
```

### 2. Cấu hình database

Tạo file `.env` trong thư mục gốc với nội dung:

```env
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/find_job_db
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Email Configuration
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# Security
JWT_SECRET=your-jwt-secret-key

# Cloudinary Configuration
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# OAuth2
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret

# Other Configuration
DEFAULT_GOOGLE_PASSWORD=defaultpassword123
APP_AUTHORIZED_REDIRECT_URI=http://localhost:5173/auth/callback
```

### 3. Chạy với Docker (Khuyến nghị)

```bash
# Khởi động database và application
docker-compose up -d

# Xem logs
docker-compose logs -f
```

### 4. Chạy thủ công

```bash
# Cài đặt dependencies
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

Ứng dụng sẽ chạy tại `http://localhost:8080`

## 📚 API Documentation

### Authentication Endpoints

```
POST /api/auth/register          # Đăng ký tài khoản
POST /api/auth/login             # Đăng nhập
PUT  /api/auth/verify/{id}       # Xác thực email
GET  /api/auth/oauth2/authorize  # OAuth2 Google login
```

### User Management

```
GET  /api/account/               # Lấy thông tin tài khoản
PUT  /api/account/reset-password # Đổi mật khẩu
```

### Profile Management

```
# Candidate Profile
POST /api/candidate-profile/           # Tạo hồ sơ ứng viên
GET  /api/candidate-profile/{id}       # Lấy hồ sơ ứng viên
PUT  /api/candidate-profile/           # Cập nhật hồ sơ
PUT  /api/candidate-profile/update-avatar  # Cập nhật avatar
GET  /api/candidate-profile/filter     # Lọc ứng viên

# Employer Profile
POST /api/employer-profile/            # Tạo hồ sơ nhà tuyển dụng
GET  /api/employer-profile/{id}        # Lấy hồ sơ nhà tuyển dụng
PUT  /api/employer-profile/            # Cập nhật hồ sơ
PUT  /api/employer-profile/update-logo # Cập nhật logo
GET  /api/employer-profile/filter      # Lọc nhà tuyển dụng
```

### Job Management

```
POST /api/job/                 # Đăng việc làm
GET  /api/job/{id}             # Lấy chi tiết việc làm
PUT  /api/job/{id}             # Cập nhật việc làm
DELETE /api/job/{id}           # Xóa việc làm
GET  /api/job/                 # Lấy việc làm của nhà tuyển dụng
POST /api/job/filter           # Lọc việc làm
GET  /api/job/employer/{id}    # Lấy việc làm theo nhà tuyển dụng
```

### Application Management

```
POST /api/application/              # Ứng tuyển
GET  /api/application/              # Lấy đơn ứng tuyển của ứng viên
GET  /api/application/job/{jobId}   # Lấy đơn ứng tuyển theo job
PUT  /api/application/{id}/withdraw # Rút đơn ứng tuyển
PUT  /api/application/job/{jobId}   # Cập nhật trạng thái ứng tuyển
```

### Address Support

```
GET /api/provinces              # Lấy danh sách tỉnh/thành
GET /api/districts/{code}       # Lấy danh sách quận/huyện
```

### Social Features

```
# Candidate Following
PUT    /api/candidates/followers/{employerId}  # Follow nhà tuyển dụng
DELETE /api/candidates/followers/{employerId}  # Unfollow nhà tuyển dụng
GET    /api/candidates/followers/              # Danh sách đã follow

# Employer Following
PUT    /api/employers/followers/{candidateId}  # Follow ứng viên
DELETE /api/employers/followers/{candidateId}  # Unfollow ứng viên
GET    /api/employers/followers/               # Danh sách đã follow
```

## 🗂️ Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/findjobbe/findjobbe/
│   │   ├── config/          # Cấu hình Spring
│   │   ├── controller/      # REST Controllers
│   │   ├── enums/          # Enumerations
│   │   ├── exception/      # Exception handling
│   │   ├── factory/        # Factory patterns
│   │   ├── mapper/         # DTOs và Request/Response models
│   │   ├── model/          # JPA Entities
│   │   ├── repository/     # Data repositories
│   │   ├── security/       # Security configuration
│   │   ├── service/        # Business logic
│   │   └── utils/          # Utility classes
│   └── resources/
│       ├── application.properties  # Cấu hình ứng dụng
│       ├── static/         # Static resources
│       └── templates/      # Email templates
└── test/                   # Unit tests
```

## 🔧 Cấu hình môi trường

### Development

```properties
spring.profiles.active=dev
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Production

```properties
spring.profiles.active=prod
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
```

## 🐳 Docker Support

Dự án hỗ trợ đầy đủ Docker với:

- **Multi-stage build** cho tối ưu kích thước image
- **Docker Compose** cho việc phát triển
- **Health checks** cho monitoring
- **Volume mapping** cho persistent data

## 🧪 Testing

```bash
# Chạy tất cả tests
mvn test

# Chạy tests với coverage
mvn test jacoco:report

# Chạy integration tests
mvn verify
```

## 📊 Monitoring & Logging

- **Actuator endpoints** cho health checks
- **Structured logging** với Logback
- **Error tracking** với custom exceptions
- **Performance monitoring** với Spring Boot metrics

## 🤝 Đóng góp

1. Fork dự án
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

## 📄 License

Dự án này được phân phối dưới MIT License. Xem `LICENSE` để biết thêm thông tin.

## 👥 Tác giả

- **Tên tác giả** - [GitHub Profile](https://github.com/your-username)
- **Email** - your.email@example.com

## 🙏 Lời cảm ơn

- [Spring Boot](https://spring.io/projects/spring-boot) - Framework tuyệt vời
- [PostgreSQL](https://www.postgresql.org/) - Cơ sở dữ liệu mạnh mẽ
- [Cloudinary](https://cloudinary.com/) - Dịch vụ lưu trữ hình ảnh
- Cộng đồng Spring Boot Việt Nam

---

<div align="center">
  Được phát triển với ❤️ bởi FindJob Team
</div>
