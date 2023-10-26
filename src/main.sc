require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /
    
    

    state: Start
        q!: $regex</start>
        a: Начнём
        buttons:
            "Для записи участника" -> /Участник
            "Узнать номер" -> /Узнать номер
        intent: /Участник || onlyThisState = false, toState = "Участник"
        intent: /Номер || onlyThisState = false, toState = "Узнать номер"

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}
        go!: /Start

    state: Match
        event!: match
        a: {{$context.intent.answer}}
        
    state: Участник || sessionResult = "Участник", sessionResultColor = "#7E47D1"
        a:Введите номер участника:
        script:
            if ($parseTree._ID) {
                $temp.id = $parseTree._ID.value;
            } else {
                $temp.id = "1";
            }
        intent!: /номер участника || onlyThisState = false,toState = "номер участника"
        
        
        event!: Nomatch
        
    state: номер участника || sessionResult = "Участник", sessionResultColor = "#7E47D1"
        a: Хорошо, записала: {{$request.query}}.
        go!:/Start
        
    state: Узнать номер || sessionResult = "Start", sessionResultColor = "#7E47D1"
       
        a:Здесь {{ $entities[0] ? $parseTree._ID.value : "продукт не найден" }}
        a:Введите номер участника:
        a: {{$request.query}}
        script:
            var numb = $request.query
        if: numb == $temp.id
            a: Есть номер
        else:
            a: В запросе нет номера.
        
