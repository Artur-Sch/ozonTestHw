#language: ru
Функционал: Тест Ozon.ru


  Предыстория:
    Когда Открыть сайт ozon.ru

  @all @ozon

  Структура сценария: Проверка работы с товарами, сценарий <count>

    * На странице - "Главная" нажать "Войти"
    * Нажать "Войти по почте", ввести данные и нажать "Вoйти"
      | Логин  | s.artur@ngs.ru |
      | Пароль | aplanaTest     |
    * В "Поле поиска" ввести "<search>"
    * На странице - "Поиск" в поле "Цена до" ввести "<maxPrice>"
    * Нажать "<button>" бренды и выбрать
      | Beats   |
      | Samsung |
    * Из результатов поиска добавить первые 8 "<isEven>" товаров в корзину
    * Перейти на страницу - "Корзина"
    * Проверить, что все добавленные ранее товары находятся в корзине
    * Проверить, что "Итоговая сумма" равна сумме цен добавленных товаров
    * Нажать на "Удалить выбранные" товары и подтвердить нажав на кнопку "Удалить"
    * Нажать на "Кабинет" и "Выйти"
    * На странице - "Корзина" нажать "Войти"
    * Нажать "Войти по почте", ввести данные и нажать "Вoйти"
      | Логин  | s.artur@ngs.ru |
      | Пароль | aplanaTest     |
    * Проверить, что "Корзина пуста" и не содержит никаких товаров

    Примеры:
      | search                | maxPrice | isEven   | button         | count |
      | Iphone                | 60000    | нечетных | none           | 1     |
      | Беспроводные наушники | 10000    | четных   | Посмотреть все | 2     |