
CREATE TABLE public.history_logs (
    id bigserial PRIMARY KEY,
    log_type varchar(10),
    table_id varchar(64),
    field_id varchar(64),
    object_id text,
    new_value text,
    old_value text,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT check_log_type check (log_type in ('INSERT', 'UPDATE', 'DELETE'))
);
