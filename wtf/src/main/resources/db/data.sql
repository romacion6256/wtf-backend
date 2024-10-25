INSERT INTO branch (id, name) VALUES (1, 'PUNTA CARRETAS') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (2, 'CIUDAD VIEJA') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (3, 'POCITOS') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (4, 'CARRASCO') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (5, 'TRES CRUCES') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (6, 'CENTRO') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (7, 'MALVIN') ON CONFLICT (id) DO NOTHING;
INSERT INTO branch (id, name) VALUES (8, 'BUCEO') ON CONFLICT (id) DO NOTHING;

INSERT INTO room (room_id, number, branch_id) VALUES (1, 1, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (2, 2, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (3, 3, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (4, 4, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (5, 5, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (6, 6, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (7, 7, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (8, 8, 1) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (9, 1, 2) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (10, 2, 2) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (11, 3, 2) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (12, 4, 2) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (13, 5, 2) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (14, 1, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (15, 2, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (16, 3, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (17, 4, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (18, 5, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (19, 6, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (20, 7, 3) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (21, 1, 4) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (22, 2, 4) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (23, 3, 4) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (24, 4, 4) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (25, 1, 5) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (26, 2, 5) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (27, 3, 5) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (28, 4, 5) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (29, 5, 5) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (30, 6, 5) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (31, 1, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (32, 2, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (33, 3, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (34, 4, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (35, 5, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (36, 6, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (37, 7, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (38, 8, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (39, 9, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (40, 10, 6) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (41, 1, 7) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (42, 2, 7) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (43, 3, 7) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (44, 1, 8) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (45, 2, 8) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (46, 3, 8) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (47, 4, 8) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (48, 5, 8) ON CONFLICT (room_id) DO NOTHING;
INSERT INTO room (room_id, number, branch_id) VALUES (49, 6, 8) ON CONFLICT (room_id) DO NOTHING;