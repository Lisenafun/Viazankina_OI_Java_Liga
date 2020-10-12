-- Drop table

-- DROP TABLE public.schools;

CREATE TABLE public.schools (
	id int4 NOT NULL,
	"name" varchar(100) NOT NULL,
	address varchar(255) NULL,
	CONSTRAINT schools_pkey PRIMARY KEY (id)
);


-- public.subjects definition

-- Drop table

-- DROP TABLE public.subjects;

CREATE TABLE public.subjects (
	id int4 NOT NULL,
	"name" varchar(100) NOT NULL,
	CONSTRAINT subjects_pkey PRIMARY KEY (id)
);


-- public.students definition

-- Drop table

-- DROP TABLE public.students;

CREATE TABLE public.students (
	id int4 NOT NULL,
	fio varchar(100) NOT NULL,
	age int4 NULL,
	gender varchar(50) NULL,
	school_id int4 NOT NULL,
	CONSTRAINT students_pkey PRIMARY KEY (id),
	CONSTRAINT students_school_id_fkey FOREIGN KEY (school_id) REFERENCES schools(id)
);


-- public.students_sub definition

-- Drop table

-- DROP TABLE public.students_sub;

CREATE TABLE public.students_sub (
	student_id int4 NOT NULL,
	subject_id int4 NOT NULL,
	CONSTRAINT students_sub_student_id_fkey FOREIGN KEY (student_id) REFERENCES students(id),
	CONSTRAINT students_sub_subject_id_fkey FOREIGN KEY (subject_id) REFERENCES subjects(id)
);


-- public.teachers definition

-- Drop table

-- DROP TABLE public.teachers;

CREATE TABLE public.teachers (
	id int4 NOT NULL,
	fio varchar(100) NOT NULL,
	age int4 NULL,
	gender varchar(50) NULL,
	school_id int4 NOT NULL,
	CONSTRAINT teachers_pkey PRIMARY KEY (id),
	CONSTRAINT teachers_school_id_fkey FOREIGN KEY (school_id) REFERENCES schools(id)
);


-- public.teachers_sub definition

-- Drop table

-- DROP TABLE public.teachers_sub;

CREATE TABLE public.teachers_sub (
	teacher_id int4 NOT NULL,
	subject_id int4 NOT NULL,
	CONSTRAINT teachers_sub_subject_id_fkey FOREIGN KEY (subject_id) REFERENCES subjects(id),
	CONSTRAINT teachers_sub_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES teachers(id)
);


