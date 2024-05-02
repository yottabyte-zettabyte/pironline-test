
CREATE TABLE public.history_logs (
    id bigserial PRIMARY KEY,
    log_type varchar(10),
    table_id varchar(64),
    object_id text,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT check_log_type check (log_type in ('INSERT', 'UPDATE', 'DELETE'))
);

CREATE TABLE public.history_logs_values (
    id bigserial PRIMARY KEY,
    parent_log_id bigserial NOT NULL,
    field_id varchar(64),
    new_value text,
    old_value text,
    CONSTRAINT fk_history_logs FOREIGN KEY (parent_log_id)
        REFERENCES history_logs (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
