CREATE TABLE IF NOT EXISTS patient (
                                       id UUID PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(1000) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(20) NOT NULL,
    registration_date DATE NOT NULL
    );
INSERT INTO patient (id, name, email, address, birth_date, gender, registration_date) VALUES
                                                                                          ('11111111-1111-1111-1111-111111111111', 'Ayaan Rahman', 'ayaan.rahman@example.com', 'Dhaka, Bangladesh', '1990-03-12', 'Male', '2024-01-10'),
                                                                                          ('22222222-2222-2222-2222-222222222222', 'Mariya Islam', 'mariya.islam@example.com', 'Chittagong, Bangladesh', '1995-07-24', 'Female', '2024-02-15'),
                                                                                          ('33333333-3333-3333-3333-333333333333', 'Naim Hasan', 'naim.hasan@example.com', 'Sylhet, Bangladesh', '1988-11-02', 'Male', '2024-03-05'),
                                                                                          ('44444444-4444-4444-4444-444444444444', 'Sadia Ahmed', 'sadia.ahmed@example.com', 'Khulna, Bangladesh', '1992-06-18', 'Female', '2024-04-10'),
                                                                                          ('55555555-5555-5555-5555-555555555555', 'Imran Hossain', 'imran.hossain@example.com', 'Rajshahi, Bangladesh', '1985-02-09', 'Male', '2024-05-02'),
                                                                                          ('66666666-6666-6666-6666-666666666666', 'Tasnim Akter', 'tasnim.akter@example.com', 'Barishal, Bangladesh', '1993-09-21', 'Female', '2024-06-08'),
                                                                                          ('77777777-7777-7777-7777-777777777777', 'Farhan Uddin', 'farhan.uddin@example.com', 'Rangpur, Bangladesh', '1998-01-17', 'Male', '2024-07-22'),
                                                                                          ('88888888-8888-8888-8888-888888888888', 'Nusrat Jahan', 'nusrat.jahan@example.com', 'Mymensingh, Bangladesh', '1997-05-28', 'Female', '2024-08-10'),
                                                                                          ('99999999-9999-9999-9999-999999999999', 'Zahid Karim', 'zahid.karim@example.com', 'Cumilla, Bangladesh', '1991-10-13', 'Male', '2024-09-04'),
                                                                                          ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Rafi Chowdhury', 'rafi.chowdhury@example.com', 'Narsingdi, Bangladesh', '1989-04-11', 'Male', '2024-10-16'),
                                                                                          ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Sumaiya Rahim', 'sumaiya.rahim@example.com', 'Gazipur, Bangladesh', '1994-12-03', 'Female', '2024-11-01'),
                                                                                          ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Arif Mahmud', 'arif.mahmud@example.com', 'Bogura, Bangladesh', '1996-07-19', 'Male', '2024-12-09'),
                                                                                          ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Tania Khatun', 'tania.khatun@example.com', 'Jessore, Bangladesh', '1999-08-25', 'Female', '2025-01-05'),
                                                                                          ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Habib Rahman', 'habib.rahman@example.com', 'Narayanganj, Bangladesh', '1987-11-30', 'Male', '2025-02-18'),
                                                                                          ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'Rumana Akhter', 'rumana.akhter@example.com', 'Cox’s Bazar, Bangladesh', '1993-03-14', 'Female', '2025-03-27');
