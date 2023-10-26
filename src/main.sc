require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём
        buttons:
            "Участник" -> /Участник
            "Номер" -> /Номер
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
        
        intent:/номер участника || onlyThisState = false,toState = "номер участника"
        event!: Nomatch
        
    state: номер участника || sessionResult = "номер участника", sessionResultColor = "#7E47D1"
        a: Хорошо, записала: {{$request.query}}.
        go!:/Start
        
    state: Узнать номер || sessionResult = "Узнать номер", sessionResultColor = "#7E47D1"
       
        a:Введите номер учатника:
        if: $entities[0]
            a: Есть номер
        else:
            a: В запросе нет номера.
