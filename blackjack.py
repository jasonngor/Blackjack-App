import random

class Card(object):
    """Represents a standard playing card."""

    def __init__(self, suit=0, rank=2):
        self.suit = suit
        self.rank = rank

    suit_names = ['Clubs','Diamonds','Hearts','Spades']
    rank_names = [None, 'Ace','2','3','4','5','6','7','8','9','10',
    'Jack','Queen','King']



    def __str__(self):
        return '{} of {}'.format(Card.rank_names[self.rank],
                                 Card.suit_names[self.suit])

    def __lt__(self,other):
        t1 = self.suit, self.rank
        t2 = other.suit, other.rank
        return t1 < t2

    def __add__(self,other):
        total = self.value + other.value
        return total

two_spades = Card(3,2)
ace_spades = Card(3,1)

class Deck(object):

    def __init__(self):
        self.cards = []
        for suit in range(4):
            for rank in range(1,14):
                card = Card(suit,rank)
                self.cards.append(card)

    def __str__(self):
        res = []
        for card in self.cards:
            res.append(str(card))
        return '\n'.join(res)

    def pop_card(self):
        return self.cards.pop()

    def add_card(self,card):
        self.cards.append(card)

    def shuffle(self):
        random.shuffle(self.cards)

    def sort(self):
        self.cards.sort()

    def move_cards(self,hand,num):
        for i in range(num):
            hand.add_card(self.pop_card())

    def value(self):
        self.totalValue = 0
        aceCount = 0
        for card in self.cards:
            if card.rank in range(2,11):
                card.cardValue = card.rank
            elif card.rank in range(11,14):
                card.cardValue = 10
            elif card.rank == 1:
                aceCount += 1
                card.cardValue = 0
            self.totalValue += card.cardValue

        for ace in range(aceCount):
            if (self.totalValue + 11) > 21:
                if (self.totalValue + 10) > 21:
                    self.totalValue += 1
                else:
                    self.totalValue += 10
            else:
                self.totalValue += 11
        return self.totalValue

class Hand(Deck):
    """Represents a hand of playing cards."""

    def __init__(self, label=''):
        self.cards = []
        self.label = label



cash = 100
print('You have ${}.'.format(cash))

while True:
    flag = False
    while flag == False:
        betAmt = int(input('How much would you like to bet? (1-20) '))
        if betAmt in range(1,21):
            bet = betAmt
            flag = True
        else:
            print('Please choose a value from 1 to 20.')


    print('You bet ${}.'.format(bet))
    # deal a new hand to player
    hand = Hand('Player Hand')
    deck = Deck()
    deck.shuffle()
    deck.move_cards(hand,2)
    print('\n' + '='*20 + '\n')
    print('Your hand contains:')
    print(hand, '\n')


    # deal a new hand to banker
    bankerHand = Hand('Banker Hand')
    deck.move_cards(bankerHand,2)

    # player's turn
    end = False

    while len(hand.cards) < 5 and not end:
        print('Your turn:\n1. Draw card.\n2. End turn.\n')
        choice = input()
        if choice == '1':
            # deal one card
            deck.move_cards(hand,1)
            print('\n' + '='*20 + '\n')
            print('Your hand contains:')
            print(hand, '\n')


        elif choice == '2':
            # end turn, bankers turn
            end = True

        else:
            print('Please enter "1" or "2".\n')


    # banker's turn
    print('\n' + '='*20 + '\n')
    bankerValue = bankerHand.value()
    while len(bankerHand.cards) < 5 and bankerValue < 16:
        deck.move_cards(bankerHand,1)
        bankerValue = bankerHand.value()


    # evaluate winner
    print('You have:')
    for card in hand.cards:
        print(card)
    print('\n')
    print('Banker has:')
    for card in bankerHand.cards:
        print(card)
    print('\n')

    if hand.value() > 21:
        hand.die = True
    else:
        hand.die = False

    if bankerHand.value() > 21:
        bankerHand.die = True
    else:
        bankerHand.die = False

    if hand.die == True and bankerHand.die == False:
        print('Sorry, you lose. Your hand is over 21 points.')
        win = False
    elif hand.die == False and bankerHand.die == True:
        print("Congratulations! You win. Banker's hand is over 21 points.")
        win = True
    elif hand.die == True and bankerHand.die == True:
        print('You escape! Both your hands are over 21 points.')
        win = 'draw'
    elif hand.value() > bankerHand.value():
        print('Congratulations! You win.')
        win = True
    elif hand.value() < bankerHand.value():
        print('Sorry, you lose. Better luck next time!')
        win = False
    elif hand.value() == bankerHand.value():
        print("It's a tie!")
        win = 'draw'

    print('\nYour points: ' + str(hand.value()) +'\n')
    print('Banker points: ' + str(bankerHand.value()) + '\n')

    if len(hand.cards) == 2 and hand.value() == 21:
        blackjack = True
        fiveCard = False
    elif len(hand.cards) == 5 and hand.value() <= 21:
        fiveCard = True
        blackjack = False
    else:
        blackjack = False
        fiveCard = False

    if win == True and fiveCard == True:
        print('Five cards below 21! You win twice your ${} bet!'.format(bet))
        cash += 2 * bet
        fiveCard = False
    elif win == True and blackjack == True:
        print('Blackjack! You win twice your ${} bet!'.format(bet))
        cash += 2 * bet
        blackjack = False
    elif win == True:
        print('You win ${}!'.format(bet))
        cash += bet
    elif win == False:
        print('You lose ${}.'.format(bet))
        cash -= bet
    elif win == 'draw':
        pass

    print('You have ${} now.'.format(cash))
    choice = input('New game? (Y/N)')
    if choice.lower() == 'n':
        break
