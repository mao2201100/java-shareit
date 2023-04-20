CREATE TABLE "users" (
                         "id" long PRIMARY KEY,
                         "name" CHARACTER,
                         "email" CHARACTER
);

CREATE TABLE "item" (
                        "id" long PRIMARY KEY,
                        "name" CHARACTER,
                        "description" CHARACTER,
                        "available" Boolean,
                        "owner_id" long,
                        "request_id" long
);


CREATE TABLE "booking" (
                           "id" long PRIMARY KEY,
                           "start_date" TIMESTAMP WITH TIME ZONE,
                           "end_date" TIMESTAMP WITHOUT TIME ZONE,
                           "item_id" long,
                           "booker_id" long,
                           "status" CHARACTER
);

CREATE TABLE "requests" (
                            "id" long PRIMARY KEY,
                            "description" CHARACTER,
                            "requestor_id" long
);

CREATE TABLE "comments" (
                            "id" long PRIMARY KEY,
                            "text" CHARACTER,
                            "item_id" long,
                            "author_id" long,
                            "created" timestamp with time zone default now()
);

ALTER TABLE "item" ADD FOREIGN KEY ("id") REFERENCES "users" ("id");

ALTER TABLE "booking" ADD FOREIGN KEY ("item_id") REFERENCES "item" ("id");

CREATE TABLE "requests_booking" (
                                    "requests_requestor_id" long,
                                    "booking_booker_id" long,
                                    PRIMARY KEY ("requests_requestor_id", "booking_booker_id")
);
ALTER TABLE "item" ADD FOREIGN KEY ("id") REFERENCES "users" ("id");

ALTER TABLE "booking" ADD FOREIGN KEY ("item_id") REFERENCES "item" ("id");

CREATE TABLE "requests_booking" (
                                    "requests_requestor_id" long,
                                    "booking_booker_id" long,
                                    PRIMARY KEY ("requests_requestor_id", "booking_booker_id")
);

ALTER TABLE "item" ADD FOREIGN KEY ("id") REFERENCES "users" ("id");

ALTER TABLE "booking" ADD FOREIGN KEY ("item_id") REFERENCES "item" ("id");

ALTER TABLE "booking" ADD FOREIGN KEY ("booker_id") REFERENCES "requests" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("item_id") REFERENCES "item" ("id");

ALTER TABLE "comments" ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

ALTER TABLE "requests" ADD FOREIGN KEY ("requestor_id") REFERENCES "users" ("id");

ALTER TABLE "requests" ADD FOREIGN KEY ("id") REFERENCES "item" ("request_id");

