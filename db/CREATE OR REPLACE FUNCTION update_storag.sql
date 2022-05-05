CREATE OR REPLACE FUNCTION update_storage_door()
RETURNS TRIGGER
AS $$
BEGIN
UPDATE item_storage
SET quantity = quantity - (SELECT quantity FROM product_receipt, receipt r WHERE
product_receipt.receipt_id = NEW.id AND r.id = NEW.id AND NEW.mounted IS NOT NULL)
WHERE storage_id = (SELECT p.storage_id FROM product p, product_receipt pr WHERE NEW.id =
pr.receipt_id AND pr.product_id=p.id) AND item_id = (SELECT door_id FROM product p, product_receipt pr, WHERE
pr.receipt_id = NEW.id AND pr.product_id=p.id AND p.door_id IS NOT NULL) ;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;
CREATE TRIGGER update_storage_door
AFTER UPDATE
ON receipt
FOR EACH ROW
EXECUTE PROCEDURE update_storage_door();

CREATE OR REPLACE FUNCTION update_storage_motor()
RETURNS TRIGGER
AS $$
BEGIN
UPDATE item_storage
SET quantity = quantity - (SELECT quantity FROM product_receipt, receipt r WHERE
product_receipt.receipt_id = NEW.id AND r.id = NEW.id AND NEW.mounted IS NOT NULL)
WHERE storage_id = (SELECT p.storage_id FROM product p, product_receipt pr WHERE NEW.id =
pr.receipt_id AND pr.product_id=p.id) AND item_id = (SELECT motor_id FROM product p, product_receipt pr, WHERE
pr.receipt_id = NEW.id AND pr.product_id=p.id AND p.motor_id IS NOT NULL) ;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;
CREATE TRIGGER update_storage_motor
AFTER UPDATE
ON receipt
FOR EACH ROW
EXECUTE PROCEDURE update_storage_motor();

CREATE OR REPLACE FUNCTION update_storage_guide()
RETURNS TRIGGER
AS $$
BEGIN
UPDATE item_storage
SET quantity = quantity - (SELECT quantity FROM product_receipt, receipt r WHERE
product_receipt.receipt_id = NEW.id AND r.id = NEW.id AND NEW.mounted IS NOT NULL)
WHERE storage_id = (SELECT p.storage_id FROM product p, product_receipt pr WHERE NEW.id =
pr.receipt_id AND pr.product_id=p.id) AND item_id = (SELECT guide_id FROM product p, product_receipt pr, WHERE
pr.receipt_id = NEW.id AND pr.product_id=p.id AND p.guide_id IS NOT NULL) ;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;
CREATE TRIGGER update_storage_guide
AFTER UPDATE
ON receipt
FOR EACH ROW
EXECUTE PROCEDURE update_storage_guide();