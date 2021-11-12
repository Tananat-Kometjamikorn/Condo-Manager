+ ####  เขียนอธิบายสรุปสิ่งที่พัฒนาในแต่ละสัปดาห์ที่ commit

สัปดาห์ที่ 1 : ได้ทำการออกแบบและพัฒนาในส่วนของ GUI  
สัปดาห์ที่ 2 : ได้พัฒนาการทำงานของโปรแกรมในการอ่านเขียนไฟล์     
สัปดาห์ที่ 3 : ได้พัฒนาการทำงานของโปรแกรมการทำงานและการแสดงผล    
สัปดาห์ที่ 4 : ได้พัฒนาการทำงานของโปรแกรม ปรับปรุงแก้ไขข้อผิดพลาดของโปรแกรม และพัฒนา GUI

---

+ #### เขียนอธิบายการวางโครงสร้างไฟล์
    
    
         -src
            | 
            |-main
               |
               |-java 
               |     |
               |     |-condo  
               |            |
               |            |--controller    //เก็บคลาสที่ควบคุมการทำงานของโปรแกรม
               |            |   |
               |            |   |---AdminLoginController //
               |            |   |
               |            |   |---AdminPageController //
               |            |   |
               |            |   |---CreditPageController //
               |            |   |
               |            |   |---InstructionController //
               |            |   |
               |            |   |---OfficerLoginController //
               |            |   |
               |            |   |---OfficerPageController //
               |            |   |
               |            |   |---StartController //
               |            | 
               |            |
               |            |--model   //เก็บคลาสจัดการกับข้อมูลภายในโปรแกรม
               |            |   |
               |            |   |---Document
               |            |   |
               |            |   |---Mail
               |            |   |
               |            |   |---MailList
               |            |   |
               |            |   |---Officer 
               |            |   |
               |            |   |---OfficerList
               |            |   |
               |            |   |---Parcel
               |            |   |
               |            |   |---Room
               |            |   |
               |            |   |---RoomList
               |            |   |
               |            |   |---User
               |            |   |
               |            |   |---UserList    
               |            | 
               |            |
               |            |--service  //เก็บคลาสที่เอาไว้จัดการกับไฟล์ข้อมูลที่ใช้ภายในโปรแกรม
               |            |   |   
               |            |   |---AdminFileDataSource
               |            |   |
               |            |   |---DocumentFileDataSource
               |            |   |
               |            |   |---MailBoxFileDataSource
               |            |   |
               |            |   |---MailFileDataSource
               |            |   |
               |            |   |---OfficerFileDataSource
               |            |   |
               |            |   |---ParcelFileDataSource
               |            |   |
               |            |   |---RoomFileDataSource
               |            |   |
               |            |   |---UserFileDataSource  
               |            |
               |            |---ProgramLauncher   
               |
               |-resources   //เก็บไฟล์ที่เอาไว้แสดงผลภายในโปรแกรม  
                       |
                       |--css   //เก็บไฟล์ css  
                       |   |   
                       |   |---button.css
                       |   |
                       |   |---font.css
                       |   |
                       |   |---picture.css
                       |   |
                       |   |---tab.css  
                       |        
                       |--fxml    //เก็บไฟล์ fxml
                       |   
                       |--picture   //เก็บไฟล์รูปภาพ


---

+ #### เขียนอธิบายวิธีเริ่มต้นใช้งาน และ username/password สำหรับทดสอบการเข้าสู่ระบบ ของ administrator, เจ้าหน้าที่ส่วนกลาง และผู้พักอาศัย (ถ้ามี)         

เมื่อเริ่มต้นใช้งานโปรแกรม ในการเข้าสู่ระบบของผู้ดูแลระบบ และเจ้าหน้าที่ส่วนกลาง มีตัวอย่างบัญชีให้ดังนี้

---

+ บัญชีเริ่มต้นสำหรับ Admin(ผู้ดูแลระบบ)

ชื่อบัญชีผู้ใช้ (Username) : cecil    
รหัสผ่าน (Password) : cecil

ชื่อบัญชีผู้ใช้ (Username) : kain    
รหัสผ่าน (Password) : kain

---

+ บัญชีเริ่มต้นสำหรับ Officer(เจ้าหน้าที่ส่วนกลาง)

ชื่อบัญชีผู้ใช้ (Username) : rose    
รหัสผ่าน (Password) : rose

ชื่อบัญชีผู้ใช้ (Username) : mild    
รหัสผ่าน (Password) : mild

---

+ ####ส่วนที่ได้แก้ไขในการแก้ไขโปรเจ็คครั้งที่ 2

1. แก้ไขข้อผิดพลาดในการเปลี่ยนรหัสผ่านของ ผู้ดูแลระบบ และเจ้าหน้าที่ส่วนกลาง 
โดยเมื่อเปลี่ยนรหัสผ่านสำเร็จจะกลับไปล็อกอิน ทำให้ไม่เกิดข้อผิดพลาด

2. แก้ไขเกี่ยวกับการทำงานในส่วนของจดหมาย เอกสาร และพัสดุ ให้มีการ inheritance และ polymorphism 
โดยการเพิ่มคลาส Document และ Parcel ซึ่ง extends มาจาก Mail และ 
เพิ่มคลาสในแพ็คเกจ service คือ interface-MailBoxFileDataSource ซึ่งนำไป implements ใน 
MailFileDataSource DocumentFileDataSource และ ParcelFileDataSource
และเมื่อนำไปใช้งานใน OfficerPageController ได้ทำให้เกิดการ polymorphism ด้วย

3. แก้ไขไฟล์ README.md

4. เพิ่มไฟล์ pdf ที่คู่มือการใช้งาน และรายละเอียดของโปรแกรม