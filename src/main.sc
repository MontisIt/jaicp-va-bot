require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    

    state: Start
       
        q!: $regex</start>
        a: Начнём.
        intent: /Пользователь || onlyThisState = false,toState = "Участник"
        intent: /Номер || onlyThisState = false,toState = "Узнать номер"

    state: NoMatch
        event!: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}
        go!: /Start

    state: Match
        event!: match
        a: {{$context.intent.answer}}
        
    state: Участник
        a:Введите номер участника:
        intent: /ID
        go:/номер участника 
        event!: Nomatch
        
    state: номер участника
        a: Хорошо, записала: {{$request.query}}.
        go!:/Start
        
    state: Узнать номер
       
        a:Введите номер учатника:
        if: $entities.pymorphy.numb.session.value
            a: Есть номер
        else:
            a: В запросе нет номера.
