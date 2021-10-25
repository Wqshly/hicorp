-- init insert data, root user and root permission --
INSERT user_info(`id`, `name`, `staff_number`, `gmt_create`) SELECT '1', 'root', 'root',current_timestamp FROM dual WHERE not exists(select 1 from user_info where user_info.id = 1);
INSERT user(`id`, user_name, `password`, `gmt_create`) SELECT '1', 'root', '$2a$10$B/uC6/8PZA28pCwdaxQ1z.LbpEe0gxCZAT0BEKngiGkSwVka85OyK', current_timestamp FROM dual WHERE not exists(select 1 from user where user.id = 1);
INSERT role(`id`, `name`, `introduction`, `create_user`, `gmt_create`) SELECT '1', 'root', '超级管理员角色', 'root', current_timestamp FROM dual WHERE not exists(select 1 from role where role.id = 1);
INSERT user_role_relation(`id`, `user_id`, `role_id`, `create_user`, `gmt_create`) SELECT '1', '1', '1', 'root', current_timestamp FROM dual WHERE not exists(select 1 from user_role_relation where user_role_relation.id = 1);
# INSERT menu(`id`, title, `index`, `create_user`, `gmt_create`) SELECT '1', 'root', '/', 'root', current_timestamp FROM dual WHERE not exists(select 1 from menu where menu.id = 1);
# INSERT role_menu_relation(`id`, `role_id`, `menu_id`, `create_user`, `gmt_create`) SELECT '1', '1', '1', 'root', current_timestamp FROM dual WHERE not exists(select 1 from role_menu_relation where role_menu_relation.id = 1);
# INSERT permission(`id`, `name`, `description`, `url`, `create_user`, `gmt_create`) SELECT '1', 'root', 'root permission', '/**', 'root',  current_timestamp FROM dual WHERE not exists(select 1 from permission where permission.id = 1);
# INSERT menu_permission_relation(`id`, `menu_id`, `permission_id`, `create_user`, `gmt_create`) SELECT '1', '1', '1', 'root', current_timestamp FROM dual WHERE not exists(select 1 from menu_permission_relation where menu_permission_relation.id = 1);