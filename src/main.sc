require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /
    
    

    state: Start
        q!: $regex</start>
        a: Начнём
        buttons:
            "Для записи участника" -> /Участник
            "Узнать номер" -> /Узнать номер
        intent: /Участник || onlyThisState = false, toState = "/Участник"
        intent: /Номер || onlyThisState = false, toState = "/Узнать номер"

    state: Участник || sessionResult = "Участник", sessionResultColor = "#7E47D1"
        a:Введите номер участника:
        q:*{{$parseTree._ID.value}}*
        intent: /номер участника || onlyThisState = false, toState = "/номер участника"
        script:
            if ($parseTree._ID) {
                $temp.id = $parseTree._ID.value;
            } else {
                $temp.id = "1";
            }
        
    state: номер участника || sessionResult = "номер участника", sessionResultColor = "#7E47D1"
        a: Хорошо, записала: {{$request.query}}.
        go!:/Проверка сущности
        
    state: Узнать номер || sessionResult = "Узнать номер", sessionResultColor = "#7E47D1"
       a:Здесь {{ $entities.$parseTree.text}}
       a:Здесь {{$parseTree._ID.value}}
      
    state: Проверка сущности || sessionResult = "Проверка сущности", sessionResultColor = "#7E47D1"
        a: {{$antitis}}
