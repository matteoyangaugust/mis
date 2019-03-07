use mis;
ALTER TABLE mis.material_of_component RENAME TO mis.elements_of_component;
ALTER TABLE mis.elements_of_component ADD `elements_type` INT DEFAULT 1 NOT NULL;
ALTER TABLE mis.elements_of_component CHANGE material_sn element_sn INT(11) NOT NULL DEFAULT '0';
ALTER TABLE mis.elements_of_component CHANGE material_amount element_amount DOUBLE NOT NULL DEFAULT '0';