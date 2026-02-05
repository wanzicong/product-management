-- Mock 1000条商品数据
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

USE product_management;

-- 使用存储过程生成1000条商品数据
DELIMITER //

DROP PROCEDURE IF EXISTS generate_mock_products//

CREATE PROCEDURE generate_mock_products()
BEGIN
    DECLARE i INT DEFAULT 11;
    DECLARE v_product_code VARCHAR(32);
    DECLARE v_product_name VARCHAR(128);
    DECLARE v_category_id INT;
    DECLARE v_brand_id INT;
    DECLARE v_price DECIMAL(10,2);
    DECLARE v_cost_price DECIMAL(10,2);
    DECLARE v_stock INT;
    DECLARE v_stock_warning INT;
    DECLARE v_unit VARCHAR(16);

    -- 商品名称前缀数组
    DECLARE name_prefix_1 VARCHAR(255) DEFAULT '智能,高端,经典,时尚,轻薄,专业,旗舰,入门,商务,游戏';
    DECLARE name_prefix_2 VARCHAR(255) DEFAULT '新款,限量,升级,豪华,精选,热销,爆款,特价,畅销,优选';

    -- 商品名称后缀 - 手机类
    DECLARE phone_names VARCHAR(500) DEFAULT 'Pro手机,Max手机,Ultra手机,Plus手机,SE手机,Mini手机,Note手机,Fold折叠屏,Flip翻盖机,青春版手机';
    -- 商品名称后缀 - 电脑类
    DECLARE computer_names VARCHAR(500) DEFAULT '笔记本电脑,游戏本,轻薄本,商务本,台式机,一体机,工作站,迷你主机,学生本,设计本';
    -- 商品名称后缀 - 平板类
    DECLARE tablet_names VARCHAR(500) DEFAULT '平板电脑,学习平板,娱乐平板,办公平板,绘画平板,阅读器,智能平板,大屏平板,护眼平板,儿童平板';
    -- 商品名称后缀 - 男装类
    DECLARE men_clothes VARCHAR(500) DEFAULT 'T恤,衬衫,夹克,外套,牛仔裤,休闲裤,运动裤,卫衣,羽绒服,西装';
    -- 商品名称后缀 - 女装类
    DECLARE women_clothes VARCHAR(500) DEFAULT '连衣裙,半身裙,衬衫,针织衫,外套,大衣,牛仔裤,阔腿裤,卫衣,风衣';
    -- 商品名称后缀 - 零食类
    DECLARE snack_names VARCHAR(500) DEFAULT '坚果礼盒,薯片,饼干,巧克力,糖果,果干,肉脯,海苔,蛋糕,面包';
    -- 商品名称后缀 - 饮料类
    DECLARE drink_names VARCHAR(500) DEFAULT '矿泉水,果汁,奶茶,咖啡,茶饮,功能饮料,碳酸饮料,乳酸菌,豆奶,椰汁';

    WHILE i <= 1010 DO
        -- 生成商品编码
        SET v_product_code = CONCAT('P', LPAD(i, 4, '0'));

        -- 随机选择分类 (5=手机, 6=电脑, 7=平板, 8=男装, 9=女装, 10=零食, 11=饮料)
        SET v_category_id = 5 + FLOOR(RAND() * 7);

        -- 根据分类选择品牌
        CASE v_category_id
            WHEN 5 THEN SET v_brand_id = 1 + FLOOR(RAND() * 4); -- 手机: 苹果/华为/小米/三星
            WHEN 6 THEN SET v_brand_id = 1 + FLOOR(RAND() * 4); -- 电脑: 苹果/华为/小米/三星
            WHEN 7 THEN SET v_brand_id = 1 + FLOOR(RAND() * 4); -- 平板: 苹果/华为/小米/三星
            WHEN 8 THEN SET v_brand_id = 5 + FLOOR(RAND() * 2); -- 男装: 耐克/阿迪达斯
            WHEN 9 THEN SET v_brand_id = 5 + FLOOR(RAND() * 2); -- 女装: 耐克/阿迪达斯
            ELSE SET v_brand_id = 1 + FLOOR(RAND() * 6); -- 其他: 随机品牌
        END CASE;

        -- 生成商品名称
        SET @prefix1_idx = 1 + FLOOR(RAND() * 10);
        SET @prefix2_idx = 1 + FLOOR(RAND() * 10);
        SET @name_idx = 1 + FLOOR(RAND() * 10);

        SET @prefix1 = SUBSTRING_INDEX(SUBSTRING_INDEX(name_prefix_1, ',', @prefix1_idx), ',', -1);
        SET @prefix2 = SUBSTRING_INDEX(SUBSTRING_INDEX(name_prefix_2, ',', @prefix2_idx), ',', -1);

        CASE v_category_id
            WHEN 5 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(phone_names, ',', @name_idx), ',', -1);
            WHEN 6 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(computer_names, ',', @name_idx), ',', -1);
            WHEN 7 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(tablet_names, ',', @name_idx), ',', -1);
            WHEN 8 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(men_clothes, ',', @name_idx), ',', -1);
            WHEN 9 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(women_clothes, ',', @name_idx), ',', -1);
            WHEN 10 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(snack_names, ',', @name_idx), ',', -1);
            WHEN 11 THEN SET @suffix = SUBSTRING_INDEX(SUBSTRING_INDEX(drink_names, ',', @name_idx), ',', -1);
            ELSE SET @suffix = '商品';
        END CASE;

        SET v_product_name = CONCAT(@prefix1, @prefix2, @suffix);

        -- 根据分类设置价格范围和单位
        CASE v_category_id
            WHEN 5 THEN -- 手机
                SET v_price = 1999 + FLOOR(RAND() * 8000);
                SET v_unit = '台';
            WHEN 6 THEN -- 电脑
                SET v_price = 3999 + FLOOR(RAND() * 15000);
                SET v_unit = '台';
            WHEN 7 THEN -- 平板
                SET v_price = 1999 + FLOOR(RAND() * 8000);
                SET v_unit = '台';
            WHEN 8 THEN -- 男装
                SET v_price = 99 + FLOOR(RAND() * 900);
                SET v_unit = '件';
            WHEN 9 THEN -- 女装
                SET v_price = 99 + FLOOR(RAND() * 900);
                SET v_unit = '件';
            WHEN 10 THEN -- 零食
                SET v_price = 9.9 + FLOOR(RAND() * 200);
                SET v_unit = '袋';
            WHEN 11 THEN -- 饮料
                SET v_price = 3 + FLOOR(RAND() * 50);
                SET v_unit = '瓶';
            ELSE
                SET v_price = 99 + FLOOR(RAND() * 500);
                SET v_unit = '个';
        END CASE;

        -- 成本价为售价的60%-80%
        SET v_cost_price = v_price * (0.6 + RAND() * 0.2);

        -- 库存随机 10-500
        SET v_stock = 10 + FLOOR(RAND() * 490);

        -- 库存预警值为库存的10%-20%
        SET v_stock_warning = GREATEST(5, FLOOR(v_stock * (0.1 + RAND() * 0.1)));

        -- 插入数据
        INSERT INTO t_product (product_code, product_name, category_id, brand_id, price, cost_price, stock, stock_warning, unit, status)
        VALUES (v_product_code, v_product_name, v_category_id, v_brand_id, v_price, v_cost_price, v_stock, v_stock_warning, v_unit, 1);

        SET i = i + 1;
    END WHILE;
END//

DELIMITER ;

-- 执行存储过程
CALL generate_mock_products();

-- 删除存储过程
DROP PROCEDURE IF EXISTS generate_mock_products;

-- 查看生成结果
SELECT COUNT(*) as total_products FROM t_product;
