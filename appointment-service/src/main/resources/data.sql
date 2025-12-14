CREATE TABLE IF NOT EXISTS cached_patient (
                                              id UUID PRIMARY KEY,
                                              full_name TEXT,
                                              email TEXT,
                                              updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS appointment(
                                          id UUID PRIMARY KEY,
                                          patient_id UUID,
                                          start_time TIMESTAMP,
                                          end_time TIMESTAMP,
                                          reason TEXT,
                                          version BIGINT DEFAULT 0 NOT NULL
);

INSERT INTO cached_patient (id, full_name, email, updated_at)
SELECT
    '11111111-1111-1111-1111-111111111111',
    'Ayaan Rahman',
    'ayaan.rahman@example.com',
    '2024-01-10 00:00:00'

WHERE NOT EXISTS (
    SELECT 1 FROM cached_patient WHERE id = '11111111-1111-1111-1111-111111111111'
)

INSERT INTO appointment(id, patient_id, start_time, end_time, reason, version)
SELECT
    '11111111-1111-1111-1111-111111111112',
    '11111111-1111-1111-1111-111111111111',
    '2024-01-10 00:00:00',
    '2024-01-10 01:00:00',
    'Initial Consultation',
    0
WHERE NOT EXISTS (
    SELECT 1 FROM appointment WHERE id = '11111111-1111-1111-1111-111111111112'
)


