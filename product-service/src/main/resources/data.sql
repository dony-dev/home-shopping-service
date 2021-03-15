-- tb_product_category
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 001', 'test category description 001');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 002', 'test category description 002');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 003', 'test category description 003');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 004', 'test category description 004');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 005', 'test category description 005');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 006', 'test category description 006');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 007', 'test category description 007');
INSERT INTO tb_product_category (category_name, category_description) VALUES ('Test Category 008', 'test category description 008');

-- tb_product
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-001', 'test product description 1-001', 12345, 1);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-002', 'test product description 1-002', 12345, 1);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-003', 'test product description 1-003', 12345, 1);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-004', 'test product description 1-004', 12345, 1);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-005', 'test product description 1-005', 12345, 1);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-006', 'test product description 1-006', 12345, 1);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 1-007', 'test product description 1-007', 12345, 1);

INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-001', 'test product description 2-001', 12345, 2);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-002', 'test product description 2-002', 12345, 2);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-003', 'test product description 2-003', 12345, 2);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-004', 'test product description 2-004', 12345, 2);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-005', 'test product description 2-005', 12345, 2);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-006', 'test product description 2-006', 12345, 2);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 2-007', 'test product description 2-007', 12345, 2);

INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-001', 'test product description 3-001', 12345, 3);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-002', 'test product description 3-002', 12345, 3);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-003', 'test product description 3-003', 12345, 3);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-004', 'test product description 3-004', 12345, 3);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-005', 'test product description 3-005', 12345, 3);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-006', 'test product description 3-006', 12345, 3);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 3-007', 'test product description 3-007', 12345, 3);

INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-001', 'test product description 4-001', 12345, 4);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-002', 'test product description 4-002', 12345, 4);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-003', 'test product description 4-003', 12345, 4);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-004', 'test product description 4-004', 12345, 4);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-005', 'test product description 4-005', 12345, 4);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-006', 'test product description 4-006', 12345, 4);
INSERT INTO tb_product (product_name, product_description, product_price, category_id) VALUES ('test product 4-007', 'test product description 4-007', 12345, 4);


-- tb_product_comment
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-1', 1);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-2', 1);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-3', 1);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-4', 1);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-5', 1);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-6', 1);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 1-7', 1);

INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-1', 2);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-2', 2);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-3', 2);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-4', 2);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-5', 2);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-6', 2);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 2-7', 2);

INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-1', 3);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-2', 3);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-3', 3);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-4', 3);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-5', 3);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-6', 3);
INSERT INTO tb_product_comment (text, product_id) VALUES ('test comment to productId 3-7', 3);