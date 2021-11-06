getContext возвращает Context, но может вернуть null, а requireContext не может, выбросит исключение IllegalStateException.
getActivity возвращает FragmentActivity, но может вернуть null, а requireActivity не может, выбросит исключение IllegalStateException.

Интерфейсы Serializable и Parcelable используются для передачи объектов между сущностями. Различия:
 Serializable - более простой в реализации, но более ресурсоемкий интерфейс. Стандартный интерфейс Java.
 Pracelable - рекомендуемый интерфейс Android SDK, более сложный в реализации, т.к. содержит boiler plate code, но требует значительно меньше времени для выполнения, более оптимален для работы с большим объемом данных.