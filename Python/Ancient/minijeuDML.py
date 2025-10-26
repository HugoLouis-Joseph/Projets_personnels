import pygame
import random
ECR = (1000,1000)
fin = False
c = 0

#Idée provenant du jeu Dragon Mania Legends
#----- pygame setup
pygame.init()
screen = pygame.display.set_mode(ECR)
clock = pygame.time.Clock()
running = True
dt = 0
player_pos = pygame.Vector2(screen.get_width() / 2, screen.get_height() / 2)
#-----
def cartedep(pos,direc):
    ligne = pos//3
    colonne = pos%3
    
    
    if direc == 'z':
        if ligne == 1:
            Lcases[pos+3].change(pos)
            Lcases[pos] = Lcases[pos+3]
            Lcases[pos+3] = case(pos+3)
        else:
            if colonne == 0:
                Lcases[pos+1].change(pos)
                Lcases[pos+2].change(pos+1)
                Lcases[pos],Lcases[pos+1] = Lcases[pos+1],Lcases[pos+2]
                Lcases[pos+2] = case(pos+2)
            elif colonne == 2:
                Lcases[pos-1].change(pos)
                Lcases[pos-2].change(pos-1)
                Lcases[pos],Lcases[pos-1] = Lcases[pos-1],Lcases[pos-2]
                Lcases[pos-2] = case(pos-2)
            else:
                if random.randint(0,1) == 0:
                    Lcases[pos-1].change(pos)
                    Lcases[pos] = Lcases[pos-1]
                    Lcases[pos-1] = case(pos-1)
                else:
                    Lcases[pos+1].change(pos)
                    Lcases[pos] = Lcases[pos+1]
                    Lcases[pos+1] = case(pos+1)
    elif direc == 's':
        if ligne == 1:
            Lcases[pos-3].change(pos)
            Lcases[pos] = Lcases[pos-3]
            Lcases[pos-3] = case(pos-3)
        else:
            if colonne == 0:
                Lcases[pos+1].change(pos)
                Lcases[pos+2].change(pos+1)
                Lcases[pos],Lcases[pos+1] = Lcases[pos+1],Lcases[pos+2]
                Lcases[pos+2] = case(pos+2)
            elif colonne == 2:
                Lcases[pos-1].change(pos)
                Lcases[pos-2].change(pos-1)
                Lcases[pos],Lcases[pos-1] = Lcases[pos-1],Lcases[pos-2]
                Lcases[pos-2] = case(pos-2)
            else:
                if random.randint(0,1) == 0:
                    Lcases[pos-1].change(pos)
                    Lcases[pos] = Lcases[pos-1]
                    Lcases[pos-1] = case(pos-1)
                else:
                    Lcases[pos+1].change(pos)
                    Lcases[pos] = Lcases[pos+1]
                    Lcases[pos+1] = case(pos+1)
    elif direc == 'q':
        if colonne == 1:
            Lcases[pos+1].change(pos)
            Lcases[pos] = Lcases[pos+1]
            Lcases[pos+1] = case(pos+1)
        else:
            if ligne == 0:
                Lcases[pos+3].change(pos)
                Lcases[pos+6].change(pos+3)
                Lcases[pos],Lcases[pos+3] = Lcases[pos+3],Lcases[pos+6]
                Lcases[pos+6] = case(pos+6)
            elif ligne == 2:
                Lcases[pos-3].change(pos)
                Lcases[pos-6].change(pos-3)
                Lcases[pos],Lcases[pos-3] = Lcases[pos-3],Lcases[pos-6]
                Lcases[pos-6] = case(pos-6)
            else:
                if random.randint(0,1) == 0:
                    Lcases[pos-3].change(pos)
                    Lcases[pos] = Lcases[pos-3]
                    Lcases[pos-3] = case(pos-3)
                else:
                    Lcases[pos+3].change(pos)
                    Lcases[pos] = Lcases[pos+3]
                    Lcases[pos+3] = case(pos+3)
    elif direc == 'd':
        if colonne == 1:
            Lcases[pos-1].change(pos)
            Lcases[pos] = Lcases[pos-1]
            Lcases[pos-1] = case(pos-1)
        else:
            if ligne == 0:
                Lcases[pos+3].change(pos)
                Lcases[pos+6].change(pos+3)
                Lcases[pos],Lcases[pos+3] = Lcases[pos+3],Lcases[pos+6]
                Lcases[pos+6] = case(pos+6)
            elif ligne == 2:
                Lcases[pos-3].change(pos)
                Lcases[pos-6].change(pos-3)
                Lcases[pos],Lcases[pos-3] = Lcases[pos-3],Lcases[pos-6]
                Lcases[pos-6] = case(pos-6)
            else:
                if random.randint(0,1) == 0:
                    Lcases[pos-3].change(pos)
                    Lcases[pos] = Lcases[pos-3]
                    Lcases[pos-3] = case(pos-3)
                else:
                    Lcases[pos+3].change(pos)
                    Lcases[pos] = Lcases[pos+3]
                    Lcases[pos+3] = case(pos+3)
#-----
class case:
    def __init__(self,pos):
        self.posX = ECR[0]//9 + (ECR[0]//3)*(pos%3)
        self.tailleX = ECR[0]//9
        self.posY = ECR[1]//9 + (ECR[1]//3)*(pos//3)
        self.tailleY = ECR[1]//9
        
        x = random.randint(1,18)
        if 1<= x <= 5:
            self.info = ['E',(255,0,0),random.randint(-9,-1)]
        elif 6 <= x <= 10:
            self.info = ['B',(0,255,0),random.randint(1,11)]
        elif 11 <= x <= 15:
            self.temp = (['P',(255,0,0),random.randint(-14,-1),'A'],['P',(50,50,255),0,'I'])
            self.i = random.randint(0,1)
            self.info = self.temp[self.i]
        elif 16 <= x <= 17:
            self.info = ['C',(200,200,200),0]
        else:
            self.info = ['F',(200,200,200),0]
    
    def change(self,pos):
        self.posX = ECR[0]//9 + (ECR[0]//3)*(pos%3)
        self.posY = ECR[1]//9 + (ECR[1]//3)*(pos//3)
    
    def affiche(self):
        pygame.draw.rect(screen,(125,125,125),pygame.Rect(self.posX,self.posY,self.tailleX,self.tailleY),0)
        if self.info != None:
            pygame.draw.rect(screen,self.info[1],pygame.Rect(self.posX,self.posY,self.tailleX,self.tailleY),5)
            screen.blit(pygame.font.SysFont('segoeuisymbol',ECR[0]//27).render(str(self.info[0]),False,self.info[1]),(self.posX,self.posY-self.tailleY//2))
            screen.blit(pygame.font.SysFont('segoeuisymbol',ECR[0]//27).render(str(self.info[2]),False,self.info[1]),(self.posX+self.tailleX//6,self.posY+self.tailleY//2))

#-----
class perso:
    def __init__(self,pos,emp):
        self.pos = pos
        self.emp = emp
        self.p = 0        
        self.hp = 20
    
    def affiche(self):
        pygame.draw.rect(screen,(0,0,255),pygame.Rect(self.emp.posX+(self.emp.tailleX//4),self.emp.posY+(self.emp.tailleY//4),self.emp.tailleX//2,self.emp.tailleY//2),0)
        screen.blit(pygame.font.SysFont('segoeuisymbol',ECR[0]//27).render(str(self.hp),False,(255,0,0)),(self.emp.posX+(self.emp.tailleX//4),self.emp.posY+(self.emp.tailleY//4)))
        
    def deplace(self):
        if self.p == 0:
            if keys[pygame.K_z] and (self.pos//3) >= 1:
                self.pos -= 3
                self.emp = Lcases[self.pos]
                self.p = 10
                cartedep(self.pos+3,'z')
            elif keys[pygame.K_s] and (self.pos//3) <= 1:
                self.pos += 3
                self.emp = Lcases[self.pos]
                self.p = 10
                cartedep(self.pos-3,'s')
            elif keys[pygame.K_q] and (self.pos%3) >= 1:
                self.pos -= 1
                self.emp = Lcases[self.pos]
                self.p = 10
                cartedep(self.pos+1,'q')
            elif keys[pygame.K_d] and (self.pos%3) <= 1:
                self.pos += 1
                self.emp = Lcases[self.pos]
                self.p = 10
                cartedep(self.pos-1,'d')
        else:
            self.p -= 1
#-----
        
Lcases = [case(x) for x in range(9)]
Lcases[4].info = None
perso = perso(4,Lcases[4])

#-----
while running:
    # poll for events
    # pygame.QUIT event means the user clicked X to close your window
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
    # fill the screen with a color to wipe away anything from last frame
    screen.fill("black")
    keys = pygame.key.get_pressed()
    
    for C in Lcases:
        C.affiche()
    perso.affiche()
    screen.blit(pygame.font.SysFont('segoeuisymbol',ECR[0]//27).render(str(c),False,(255,255,255)),(ECR[0]-ECR[0]//18,ECR[1]-ECR[1]//18))
    if perso.hp > 0 and not fin:
        perso.deplace()
        if perso.p == 10:
            perso.hp = max(0,min(perso.hp + perso.emp.info[2],30))
            if perso.emp.info[0] == 'C':
                c = min(c+1,5)
            elif perso.emp.info[0] == 'F':
                fin = True
            perso.emp.info = None
            for C in Lcases:
                if C.info != None and C.info[0] == 'P':
                    C.i = (C.i+1)%2
                    C.info = C.temp[C.i]
    # flip() the display to put your work on screen
    pygame.display.flip()

    # limits FPS to 60
    # dt is delta time in seconds since last frame, used for framerate-
    # independent physics.
    dt = clock.tick(60) / 1000

pygame.quit()