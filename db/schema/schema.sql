-- ================================================================================= --
--                              ToDoService-GreenCat                                 --
-- ================================================================================= --

-- --------------------------------------------------------------------------------- --
-- Table `color`  (프로젝트 색상 분류)
-- --------------------------------------------------------------------------------- --
CREATE TABLE `color` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `hex_code` VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_color_name` (`name`),
    UNIQUE INDEX `UK_color_hex_code` (`hex_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------------------------------- --
-- Table `project`  (최상위 작업 단위)
-- --------------------------------------------------------------------------------- --
CREATE TABLE `project` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `color_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `status` VARCHAR(30) NOT NULL DEFAULT 'PLANNING',
    `description` TEXT NULL,
    `is_public` BOOLEAN NOT NULL DEFAULT TRUE,
    `visibility` VARCHAR(20) NOT NULL DEFAULT 'PRIVATE',
    `start_date` DATE NULL,
    `end_date` DATE NULL,
    `actual_end_date` DATE NULL,
    `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    INDEX `FK_project_color` (`color_id`),
    CONSTRAINT `FK_project_color`
        FOREIGN KEY (`color_id`)
        REFERENCES `color` (`id`)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------------------------------- --
-- Table `task`  (프로젝트 하위 할 일)
-- --------------------------------------------------------------------------------- --
CREATE TABLE `task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `project_id` BIGINT NOT NULL,
    `priority` VARCHAR(10) NOT NULL DEFAULT 'HIGH',
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NULL,
    `day_label` VARCHAR(255) NULL,
    `status` VARCHAR(30) NOT NULL DEFAULT 'PLANNING',
    `start_date` DATE NULL,
    `start_time` TIME NULL,
    `start_time_enabled` BOOLEAN NOT NULL DEFAULT FALSE,
    `due_date` DATE NULL,
    `due_time` TIME NULL,
    `due_time_enabled` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (`id`),
    INDEX `FK_task_project` (`project_id`),
    CONSTRAINT `FK_task_project`
        FOREIGN KEY (`project_id`)
        REFERENCES `project` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
