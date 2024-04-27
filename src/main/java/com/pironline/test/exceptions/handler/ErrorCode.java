package com.pironline.test.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {

    ERROR_BAD_REQUEST {
        @Override
        public String getValue() {
            return "error.bad_request";
        }
    },
    ERROR_EMPTY_NAMED_PARAM {
        @Override
        public String getValue() {
            return "error.empty_named_param";
        }
    },
    ERROR_EMPTY_PARAMS {
        @Override
        public String getValue() {
            return "error.empty_params";
        }
    },
    ERROR_INVALID_DATA {
        @Override
        public String getValue() {
            return "error.invalid_data";
        }
    },
    ERROR_GENERIC {
        @Override
        public String getValue() {
            return "error.generic";
        }
    },
    ERROR_NOT_FOUND {
        @Override
        public String getValue() {
            return "error.not_found";
        }
    },
    ERROR_OPTIMISTIC_LOCK {
        @Override
        public String getValue() {
            return "error.optimistic_lock";
        }
    };

    @JsonValue
    public abstract String getValue();
}
