INSERT INTO categories (created_at, updated_at, name)
VALUES (NOW(), NOW(), 'Разработка ПО'),
       (NOW(), NOW(), 'Высшая математика'),
       (NOW(), NOW(), 'Физика и астрономия'),
       (NOW(), NOW(), 'Иностранные языки'),
       (NOW(), NOW(), 'Графический дизайн');

INSERT INTO tags (created_at, updated_at, name)
VALUES (NOW(), NOW(), 'Java'),
       (NOW(), NOW(), 'Spring Framework'),
       (NOW(), NOW(), 'SQL и БД'),
       (NOW(), NOW(), 'Структуры данных'),
       (NOW(), NOW(), 'Backend разработка'),
       (NOW(), NOW(), 'Высшая математика'),
       (NOW(), NOW(), 'Технический английский');

INSERT INTO users (created_at, updated_at, name, email, role)
VALUES (NOW(), NOW(), 'Петр Иванов', 'petr.ivanov@mail.ru', 'TEACHER'),
       (NOW(), NOW(), 'Екатерина Волкова', 'ekaterina.volkova@mail.ru', 'TEACHER'),
       (NOW(), NOW(), 'Михаил Соколов', 'mikhail.sokolov@mail.ru', 'TEACHER'),
       (NOW(), NOW(), 'Анастасия Федорова', 'anastasia.fedorova@mail.ru', 'STUDENT'),
       (NOW(), NOW(), 'Максим Морозов', 'maxim.morozov@mail.ru', 'STUDENT'),
       (NOW(), NOW(), 'Дарья Павлова', 'darya.pavlova@mail.ru', 'STUDENT'),
       (NOW(), NOW(), 'Николай Кузнецов', 'nikolay.kuznetsov@mail.ru', 'STUDENT'),
       (NOW(), NOW(), 'Виктория Лебедева', 'victoria.lebedeva@mail.ru', 'STUDENT'),
       (NOW(), NOW(), 'Главный администратор', 'admin@eduplatform.ru', 'ADMIN');

INSERT INTO profiles (created_at, updated_at, user_id, bio, avatar_url)
VALUES (NOW(), NOW(), 1, 'Опытный разработчик Java с 12-летним стажем', '/avatars/petr.jpg'),
       (NOW(), NOW(), 2, 'Эксперт в области реляционных БД и алгоритмов', '/avatars/ekaterina.jpg'),
       (NOW(), NOW(), 3, 'Кандидат физико-математических наук', '/avatars/mikhail.jpg'),
       (NOW(), NOW(), 4, 'Студентка 3 курса, увлекаюсь веб-разработкой', '/avatars/anastasia.jpg'),
       (NOW(), NOW(), 5, 'Системный администратор учебной платформы', '/avatars/admin.jpg');

INSERT INTO courses (created_at, updated_at, title, description, category_id, teacher_id)
VALUES (NOW(), NOW(), 'Основы Java программирования', 'Комплексный курс для новичков по языку Java', 1, 1),
       (NOW(), NOW(), 'Разработка на Spring Boot', 'Создание современных приложений с Spring', 1, 1),
       (NOW(), NOW(), 'SQL и реляционные базы данных', 'Полное погружение в мир SQL и СУБД', 1, 2),
       (NOW(), NOW(), 'Высшая математика для IT', 'Математический анализ в программировании', 2, 3),
       (NOW(), NOW(), 'Английский язык для разработчиков', 'Технический английский в сфере IT', 4, 2);

INSERT INTO course_tags (course_id, tag_id)
VALUES (1, 1),
       (1, 4),
       (2, 1),
       (2, 2),
       (2, 5),
       (3, 3),
       (3, 4),
       (4, 6),
       (5, 7);

INSERT INTO modules (created_at, updated_at, course_id, title, order_index)
VALUES (NOW(), NOW(), 1, 'Начало работы с Java', 1),
       (NOW(), NOW(), 1, 'Принципы ООП в Java', 2),
       (NOW(), NOW(), 1, 'Работа с коллекциями и обработка исключений', 3),
       (NOW(), NOW(), 2, 'Знакомство со Spring Framework', 1),
       (NOW(), NOW(), 2, 'Создание приложений на Spring Boot', 2),
       (NOW(), NOW(), 3, 'Основы языка SQL', 1),
       (NOW(), NOW(), 3, 'Проектирование и нормализация БД', 2);

INSERT INTO lessons (created_at, updated_at, module_id, title, content, video_url)
VALUES (NOW(), NOW(), 1, 'Написание первого приложения на Java', 'Синтаксис Java и создание простейшей программы',
        '/videos/java-intro.mp4'),
       (NOW(), NOW(), 1, 'Типы данных и переменные', 'Работа с примитивными типами данных в Java',
        '/videos/java-variables.mp4'),
       (NOW(), NOW(), 2, 'Объекты и классы в Java', 'Основы ООП: создание классов, конструкторов и методов',
        '/videos/java-classes.mp4'),
       (NOW(), NOW(), 4, 'Основы Spring Framework', 'Изучение архитектуры Spring и контейнера IoC',
        '/videos/spring-intro.mp4'),
       (NOW(), NOW(), 6, 'Запросы SELECT в SQL', 'Базовые операторы выборки данных', '/videos/sql-select.mp4');

INSERT INTO materials (created_at, updated_at, lesson_id, type, title, url, description)
VALUES (NOW(), NOW(), 1, 'PDF', 'Слайды презентации', '/materials/java-intro.pdf', 'Ключевые моменты урока'),
       (NOW(), NOW(), 1, 'ARTICLE', 'Справочные материалы', 'https://docs.oracle.com/javase/tutorial/',
        'Документация Oracle по Java'),
       (NOW(), NOW(), 3, 'PDF', 'Практические задания по ООП', '/materials/oop-tasks.pdf',
        'Упражнения для закрепления'),
       (NOW(), NOW(), 4, 'VIDEO', 'Видеоматериал', '/videos/spring-ioc.mp4', 'Детальный разбор IoC контейнера'),
       (NOW(), NOW(), 5, 'PDF', 'Справочник команд SQL', '/materials/sql-cheatsheet.pdf', 'Основной синтаксис SQL');

INSERT INTO assignments (created_at, updated_at, lesson_id, title, description, due_date, max_score)
VALUES (NOW(), NOW(), 1, 'Стартовая программа', 'Создайте приложение Hello World с вводом данных через Scanner',
        '2024-02-22 23:59:59', 100),
       (NOW(), NOW(), 3, 'Разработка класса Student', 'Реализуйте класс Student с необходимыми атрибутами и методами',
        '2024-02-27 23:59:59', 100),
       (NOW(), NOW(), 5, 'Составление SQL запросов', 'Напишите различные SELECT запросы для получения данных',
        '2024-03-01 23:59:59', 100);

INSERT INTO quizzes (created_at, updated_at, module_id, title)
VALUES (NOW(), NOW(), 1, 'Проверка знаний основ Java'),
       (NOW(), NOW(), 2, 'Тестирование по объектно-ориентированному программированию'),
       (NOW(), NOW(), 6, 'Контрольный тест по SQL');

INSERT INTO questions (created_at, updated_at, quiz_id, text, type)
VALUES (NOW(), NOW(), 1, 'Какая команда применяется для печати текста в консоль Java?', 'SINGLE_CHOICE'),
       (NOW(), NOW(), 1, 'Выберите примитивные типы данных в Java?', 'MULTIPLE_CHOICE'),
       (NOW(), NOW(), 2, 'Дайте определение понятию инкапсуляция?', 'SINGLE_CHOICE'),
       (NOW(), NOW(), 3, 'Какая команда SQL служит для фильтрации строк?', 'SINGLE_CHOICE');

INSERT INTO answer_options (created_at, updated_at, question_id, text, is_correct)
VALUES (NOW(), NOW(), 1, 'System.out.println()', TRUE),
       (NOW(), NOW(), 1, 'console.log()', FALSE),
       (NOW(), NOW(), 1, 'printf()', FALSE),
       (NOW(), NOW(), 2, 'int', TRUE),
       (NOW(), NOW(), 2, 'String', FALSE),
       (NOW(), NOW(), 2, 'boolean', TRUE),
       (NOW(), NOW(), 2, 'Long', FALSE),
       (NOW(), NOW(), 3, 'Скрытие внутренней реализации', TRUE),
       (NOW(), NOW(), 3, 'Механизм наследования', FALSE),
       (NOW(), NOW(), 3, 'Инстанцирование объектов', FALSE),
       (NOW(), NOW(), 4, 'WHERE', TRUE),
       (NOW(), NOW(), 4, 'FILTER', FALSE),
       (NOW(), NOW(), 4, 'SELECT', FALSE);

INSERT INTO enrollments (created_at, updated_at, student_id, course_id, enrol_date, status)
VALUES (NOW(), NOW(), 4, 1, NOW(), 'ACTIVE'),
       (NOW(), NOW(), 5, 1, NOW(), 'ACTIVE'),
       (NOW(), NOW(), 6, 1, NOW(), 'ACTIVE'),
       (NOW(), NOW(), 4, 2, NOW(), 'ACTIVE'),
       (NOW(), NOW(), 7, 3, NOW(), 'ACTIVE'),
       (NOW(), NOW(), 8, 5, NOW(), 'ACTIVE');

INSERT INTO submissions (created_at, updated_at, assignment_id, student_id, submitted_at, content, score, feedback)
VALUES (NOW(), NOW(), 1, 4, NOW(),
        'public class HelloWorld { public static void main(String[] args) { System.out.println("Hello World"); } }', 98,
        'Превосходная работа!'),
       (NOW(), NOW(), 1, 5, NOW(),
        'public class Main { public static void main(String[] args) { Scanner sc = new Scanner(System.in); System.out.println("Hello"); } }',
        82, 'Хорошо выполнено, но необходимо закрывать Scanner');

INSERT INTO quiz_submissions (created_at, updated_at, quiz_id, student_id, score, taken_at)
VALUES (NOW(), NOW(), 1, 4, 88, NOW()),
       (NOW(), NOW(), 1, 5, 72, NOW()),
       (NOW(), NOW(), 3, 7, 93, NOW());

INSERT INTO course_reviews (created_at, updated_at, course_id, student_id, rating, comment, created_at_review)
VALUES (NOW(), NOW(), 1, 4, 5, 'Великолепный курс с понятным изложением материала!', NOW()),
       (NOW(), NOW(), 1, 5, 4, 'Качественный курс, хотелось бы больше практических заданий', NOW()),
       (NOW(), NOW(), 3, 7, 5, 'Отличный преподаватель, очень информативный курс', NOW());

INSERT INTO course_schedules (created_at, updated_at, course_id, start_date, end_date, weekday)
VALUES (NOW(), NOW(), 1, '2024-02-05', '2024-05-10', 'MONDAY'),
       (NOW(), NOW(), 1, '2024-02-05', '2024-05-10', 'WEDNESDAY'),
       (NOW(), NOW(), 2, '2024-02-20', '2024-04-20', 'TUESDAY'),
       (NOW(), NOW(), 3, '2024-03-05', '2024-06-10', 'THURSDAY');

INSERT INTO notifications (created_at, updated_at, user_id, message, read, created_at_note)
VALUES (NOW(), NOW(), 4, 'Добавлено новое задание: "Стартовая программа"', FALSE, NOW()),
       (NOW(), NOW(), 5, 'Получена оценка за выполненное задание: 82/100', TRUE, NOW()),
       (NOW(), NOW(), 4, 'Проверка завершена, результат: 98/100', TRUE, NOW()),
       (NOW(), NOW(), 7, 'Открыт доступ к модулю: "Проектирование и нормализация БД"', FALSE, NOW());