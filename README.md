# ChillShaker - Hệ Thống Đặt Bàn Quán Bar

<div align="center">
  <img src="https://i.ibb.co/your-logo-image" alt="ChillShaker Logo" width="200"/>
</div>

## Giới Thiệu
ChillShaker là một hệ thống đặt bàn quán bar trực tuyến, cho phép người dùng dễ dàng đặt bàn, chọn đồ uống và thanh toán trực tuyến. Hệ thống bao gồm hai phần chính:

- [Frontend Repository](https://github.com/ChillShaker/ChillShaker-FE.git) - Giao diện người dùng được xây dựng bằng ReactJS
- [Backend Repository](https://github.com/ChillShaker/ChillShaker-BE.git) - API được xây dựng bằng Java Spring Boot

## Tính Năng Chính
- Đặt bàn trực tuyến
- Quản lý menu đồ uống
- Thanh toán trực tuyến qua VNPay
- Xác thực người dùng qua email
- Quản lý đơn đặt bàn realtime
- Tích hợp Redis để quản lý trạng thái bàn

## Yêu Cầu Hệ Thống

### Backend
- Java JDK 21
- Maven
- PostgreSQL
- Redis
- Docker (tùy chọn)

### Frontend
- Node.js
- npm/yarn
- ReactJS

## Hướng Dẫn Cài Đặt

### Backend

1. Clone repository:
   ```bash
   git clone https://github.com/ChillShaker/ChillShaker-BE.git
   cd ChillShaker-BE
   ```

2. Cài đặt các phụ thuộc:
   ```bash
   mvn clean install
   ```

3. Tạo file .env với các biến môi trường cần thiết:
   ```bash
   https://drive.google.com/file/d/1hkXKNfJ1rOwJNr2yroF29JbOc9W0WQXf/view?usp=sharing
   ```

4. Chạy ứng dụng:
   ```bash
   mvn spring-boot:run
   ```

### Frontend

1. Clone repository:
   ```bash
   git clone https://github.com/ChillShaker/ChillShaker-FE.git
   cd ChillShaker-FE
   ```  

2. Cài đặt các phụ thuộc:
   ```bash
   npm install
   ```

3. Chạy ứng dụng:
   ```bash  
   npm start
   ```

## Giao Diện Người Dùng

<div align="center">
  <img src="https://i.ibb.co/ui-flow" alt="Luồng người dùng" width="800"/>
  <p><i>Sơ đồ luồng người dùng</i></p>
  <a href="https://www.canva.com/design/DAGeTOJ41BI/OVL4dEHh4zEmgASsyYLuxQ/edit?utm_content=DAGeTOJ41BI&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton">Canva</a>
</div>

## Liên Hệ & Hỗ Trợ

Nếu bạn có bất kỳ câu hỏi hoặc góp ý nào, vui lòng liên hệ:

- Email: ngotriduc0411@gmail.com

## License

[MIT License](LICENSE)
