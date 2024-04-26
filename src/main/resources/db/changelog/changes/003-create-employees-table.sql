
CREATE TABLE employees (
    id UUID PRIMARY KEY,
    version bigint NOT NULL DEFAULT 0,
    deleted BOOLEAN NOT NULL DEFAULT false,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    snils VARCHAR(20),
    birth_date DATE,
    gender CHAR(1),
    company_id UUID,
    title_id INTEGER,
    start_date DATE,
    leave_date DATE,
    salary_amount numeric(15,2),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_employees_company FOREIGN KEY (company_id)
        REFERENCES companies (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_employees_title FOREIGN KEY (title_id)
        REFERENCES dict_titles (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT check_gender check (gender in ('M', 'F'))
);