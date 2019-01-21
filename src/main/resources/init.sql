SELECT @@global.time_zone, @@session.time_zone;

create table tbl_user_group(
  group_id int auto_increment not null primary key ,
  parent_group_id int,
  group_name varchar(255),
  group_level int,
  create_date timestamp,
  update_date timestamp
);

insert into tbl_user_group (parent_group_id, group_name, group_level, create_date, update_date) values (-1, 'admin', 1, now(), now());
insert into tbl_user_group (parent_group_id, group_name, group_level, create_date, update_date) values (-1, 'pastor', 1, now(), now());
insert into tbl_user_group (parent_group_id, group_name, group_level, create_date, update_date) values (-1, 'group', 1, now(), now());
insert into tbl_user_group (parent_group_id, group_name, group_level, create_date, update_date) values (3, 'town', 3, now(), now());
insert into tbl_user_group (parent_group_id, group_name, group_level, create_date, update_date) values (4, 'pasture', 4, now(), now());
select * from tbl_user_group;

drop table tbl_user;
create table tbl_user(
  user_seq int auto_increment not null,
  user_id varchar(255) ,
  password varchar(255),
  group_id int,
  user_name varchar(255),
  is_admin boolean,
  address varchar(1024),
  mobile varchar(16),
  email varchar(255),
  reg_date timestamp,
  alpha_date timestamp,
  pasture_join_date timestamp,
  status varchar(255),
  create_date timestamp,
  update_date timestamp,
  PRIMARY KEY(user_seq, user_id)
);

select * from tbl_user;

create table tbl_common_code(
  code_id varchar(255) not null primary key,
  parent_code_id varchar(255),
  code_name varchar(255),
  description varchar(500)
);

insert into tbl_common_code (code_id, parent_code_id, code_name, description) VALUES ("00005", null, "사용자 상태", "사용자의 현재 상태를 나타낸다.");
insert into tbl_common_code (code_id, parent_code_id, code_name, description) VALUES ("00006", "00005", "등록", "등록된 상태");
insert into tbl_common_code (code_id, parent_code_id, code_name, description) VALUES ("00007", "00005", "로그인", "등록된 사용자가 로그인 상태");
insert into tbl_common_code (code_id, parent_code_id, code_name, description) VALUES ("00008", "00005", "로그아웃", "등록된 사용자가 로그아웃 한 상태");
insert into tbl_common_code (code_id, parent_code_id, code_name, description) VALUES ("00009", "00005", "탈퇴", "등록된 사용자가 탈퇴한 상태");
select * from tbl_common_code;

create table tbl_menu(
  menu_id int not null primary key,
  parent_menu_id int,
  menu_name varchar(255),
  menu_level int,
  sort_idx int,
  create_date timestamp default now(),
  update_date timestamp default now()
);

insert into tbl_menu (menu_id, parent_menu_id, menu_name, menu_level, sort_idx) values (1, null, "chyou 웹스토리", 0, 1);
insert into tbl_menu (menu_id, parent_menu_id, menu_name, menu_level, sort_idx) values (2, 1, "목장 스토리", 1, 1);
insert into tbl_menu (menu_id, parent_menu_id, menu_name, menu_level, sort_idx) values (3, 1, "사역자 스토리", 1, 2);
insert into tbl_menu (menu_id, parent_menu_id, menu_name, menu_level, sort_idx) values (4, 1, "관리", 1, 3);
insert into tbl_menu (menu_id, parent_menu_id, menu_name, menu_level, sort_idx) values (5, 4, "마을 관리", 2, 1);
insert into tbl_menu (menu_id, parent_menu_id, menu_name, menu_level, sort_idx) values (6, 4, "그룹 관리", 2, 2);

select * from tbl_menu;

drop table tbl_story_pastor;
create table tbl_story_pastor(
  story_id int auto_increment not null primary key ,
  pastor_id varchar(255),
  visit_user_id varchar(255),
  visit_date timestamp,
  summary varchar(1000),
  prayers varchar(1000),
  etc varchar(1000),
  create_date timestamp default now(),
  update_date timestamp default now()
);

insert into tbl_story_pastor (pastor_id, visit_user_id, visit_date, summary, prayers, etc) VALUES ("oopchoi", "oopchoi", now(), "신앙인으로서 부끄럽지 않게...", "부끄럽습니다.", "이사가요...");

select * from tbl_story_pastor;

create table tbl_story_master(
  story_id int auto_increment not null primary key ,
  worship_yn boolean,
  pasture_meet_yn boolean,
  bible_count int,
  qt_count int,
  friday_worship_yn boolean,
  dawn_pray_count int,
  prayers varchar(1000),
  etc varchar(1000),
  create_date timestamp default now(),
  update_date timestamp default now()
);

insert into tbl_story_pastor (pastor_id, visit_user_id, visit_date, summary, prayers, etc) value ("oopchoi", "oopchoi", now(), "행복")
