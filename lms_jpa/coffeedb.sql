CREATE TABLE IF NOT EXISTS tbl_category
(
    category_code    INT AUTO_INCREMENT COMMENT '카테고리코드',
    category_name    VARCHAR(30) NOT NULL COMMENT '카테고리명',
    ref_category_code    INT COMMENT '상위카테고리코드',
    CONSTRAINT pk_category_code PRIMARY KEY (category_code),
    CONSTRAINT fk_ref_category_code FOREIGN KEY (ref_category_code) REFERENCES tbl_category (category_code)
) ENGINE=INNODB COMMENT '카테고리';

CREATE TABLE IF NOT EXISTS tbl_menu
(
    menu_code    INT AUTO_INCREMENT COMMENT '메뉴코드',
    menu_name    VARCHAR(30) NOT NULL COMMENT '메뉴명',
    menu_price    INT NOT NULL COMMENT '메뉴가격',
    category_code    INT NOT NULL COMMENT '카테고리코드',
    orderable_status    CHAR(1) NOT NULL COMMENT '주문가능상태',
    CONSTRAINT pk_menu_code PRIMARY KEY (menu_code),
    CONSTRAINT fk_category_code FOREIGN KEY (category_code) REFERENCES tbl_category (category_code)
) ENGINE=INNODB COMMENT '메뉴';


INSERT INTO tbl_category VALUES (1, '커피', 1);
INSERT INTO tbl_category VALUES (2, '콜드브루', 2);
INSERT INTO tbl_category VALUES (3, '스무디', 3);
INSERT INTO tbl_category VALUES (4, '프라페', 4);
INSERT INTO tbl_category VALUES (5, '에이드', 5);
INSERT INTO tbl_category VALUES (6, '티', 6);
INSERT INTO tbl_category VALUES (7, '디저트', 7);

INSERT INTO tbl_menu VALUES (1, '아메리카노', 2500, 1, 'Y');
INSERT INTO tbl_menu VALUES (2, '카페라떼', 3400, 1, 'Y');
INSERT INTO tbl_menu VALUES (3, '카푸치노', 3400, 1, 'Y');
INSERT INTO tbl_menu VALUES (4, '헤이즐넛라떼', 3900, 1, 'Y');
INSERT INTO tbl_menu VALUES (5, '바닐라라떼', 3900, 1, 'Y');
INSERT INTO tbl_menu VALUES (6, '카라멜마끼아또', 4200, 1, 'Y');
INSERT INTO tbl_menu VALUES (7, '콜드브루오리지널', 4000, 2, 'Y');
INSERT INTO tbl_menu VALUES (8, '콜드브루라떼', 4500, 2, 'Y');
INSERT INTO tbl_menu VALUES (9, '망고요거트스무디', 4400, 3, 'Y');
INSERT INTO tbl_menu VALUES (10, '딸기요거트스무디', 4400, 3, 'Y');
INSERT INTO tbl_menu VALUES (11, '민트프라페', 4400, 4, 'Y');
INSERT INTO tbl_menu VALUES (12, '리얼초코프라페', 4400, 4, 'Y');
INSERT INTO tbl_menu VALUES (13, '레드오렌지자몽주스', 4500, 5, 'Y');
INSERT INTO tbl_menu VALUES (14, '샤인머스켓주스', 4500, 5, 'Y');
INSERT INTO tbl_menu VALUES (15, '딸기바나나주스', 4500, 5, 'Y');
INSERT INTO tbl_menu VALUES (16, '복숭아아이스티', 3500, 6, 'Y');
INSERT INTO tbl_menu VALUES (17, '허니자몽블랙티', 4200, 6, 'Y');
INSERT INTO tbl_menu VALUES (18, '자몽티', 3800, 6, 'Y');
INSERT INTO tbl_menu VALUES (19, '슈크림빵', 3200, 7, 'Y');
INSERT INTO tbl_menu VALUES (20, '초코스모어쿠키', 3400, 7, 'Y');

COMMIT;