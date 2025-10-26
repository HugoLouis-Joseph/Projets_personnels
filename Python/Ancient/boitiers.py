import pygame
import random
ECR = (1280,1000)
#----- pygame setup
pygame.init()
screen = pygame.display.set_mode(ECR)
clock = pygame.time.Clock()
running = True
dt = 0
player_pos = pygame.Vector2(screen.get_width() / 2, screen.get_height() / 2)
#-----
class case:
    def __init__(self,position,etat,col=(255,255,255),val=None,caseG=None,caseH=None,caseD=None,caseB=None):
        self.X = position[0]
        self.Y = position[1]
        self.etat = etat
        if self.etat == 'debut':
            self.col = col
            self.LS = []
            self.fini = False
            self.val = val
        elif self.etat == 'fin':
            self.col = col
            self.val = val
        elif self.etat == 'inactif':
            self.col = (125,25,25)
        else:
            self.col = (125,125,125)
        
        self.G = caseG
        self.H = caseH
        self.D = caseD
        self.B = caseB
        self.CM = ''
        self.S = ''
        
        self.visible = False
    
    def actionC(self,emp):
        self.affiche(emp)
        self.desaffiche()
    
    def affiche(self,emp):
        self.visible = True
        if self.CM == '':
            pygame.draw.rect(screen,self.col,pygame.Rect(self.X,self.Y,30,30),0)
        else:
            pygame.draw.rect(screen,self.CM.col,pygame.Rect(self.X,self.Y,30,30),0)
        if emp == self:
             pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.X,self.Y,30,30),2)
        
        if self.etat == 'debut':
            self.continu()
        
        if self.etat == 'debut' or self.etat == 'fin' :
            screen.blit(pygame.font.SysFont('segoeuisymbol',20).render(str(self.val),False,(255-self.col[0],255-self.col[1],255-self.col[2])),(self.X+10,self.Y))
        
        if self.G == self.S :
                pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.X-20,self.Y+15,20,2),0)
        if self.H == self.S :
                pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.X+15,self.Y-20,2,20),0)
        if self.D == self.S :
                pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.X+30,self.Y+15,20,2),0)
        if self.B == self.S :
                pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.X+15,self.Y+30,2,20),0)
        
        if self.G != None and self.G.visible == False:
            self.G.affiche(emp)
        if self.H != None and self.H.visible == False:
            self.H.affiche(emp)
        if self.D != None and self.D.visible == False:
            self.D.affiche(emp)
        if self.B != None and self.B.visible == False:
            self.B.affiche(emp)
    
    def desaffiche(self):
        self.visible = False
            
        if self.G != None and self.G.visible == True:
            self.G.desaffiche()
        if self.H != None and self.H.visible == True:
            self.H.desaffiche()
        if self.D != None and self.D.visible == True:
            self.D.desaffiche()
        if self.B != None and self.B.visible == True:
            self.B.desaffiche()
    
    def continu(self):
        temp = self
        for i in range(1,len(self.LS)) :
            suivant = temp.LS[i]
            self.S = suivant
            self.CM = temp
            self = self.S
        self = temp
        if len(self.LS) > 0 and self.LS[-1].S != '':
            self.LS[-1].retire()
    
    def retire(self):
        if self.S != '':
            self.S.retire()
        self.S = ''
        self.CM = ''
    
    def changevoisins(self,G,H,D,B):
        if G != None:
            G.D = self
            self.G = G
        if H != None:
            H.B = self
            self.H = H
        if D != None:
            D.G = self
            self.D = D
        if B != None:
            B.H = self
            self.B = B
#-----

def generation(L,C,gen,NBC):
    liste = []
    for _ in range(L):
        liste.append([])

    for y in range(L):
        for x in range(C):
            liste[y].append(case((50*(x+1),50*(y+1)),'actif'))

    listetemp = [case((0,0),'debut',(0,0,255),0),case((0,0),'fin',(0,0,200),0),case((0,0),
                'debut',(255,0,0),1),case((0,0),'fin',(200,0,0),1),case((0,0),
                'debut',(0,255,0),2),case((0,0),'fin',(0,200,0),2),case((0,0),
                'debut',(0,255,255),3),case((0,0),'fin',(0,200,200),3),case((0,0),
                'debut',(255,0,255),4),case((0,0),'fin',(200,0,200),4),case((0,0),
                'debut',(255,255,0),5),case((0,0),'fin',(200,200,0),5),case((0,0),
                'debut',(255,255,255),6),case((0,0),'fin',(200,200,200),6),case((0,0),
                'debut',(255,125,255),7),case((0,0),'fin',(200,70,200),7),case((0,0),
                'debut',(125,255,255),8),case((0,0),'fin',(70,200,200),8),case((0,0),
                'debut',(255,255,125),9),case((0,0),'fin',(200,200,70),9)]
    listetemp = listetemp[:NBC*2]
    if gen == 1:
        while listetemp != []:
            y = random.randint(1,len(liste)-2)
            x = random.randint(1,len(liste[0])-2)
            while liste[y][x].etat != 'actif' or liste[y][x-1].etat != 'actif' or liste[y][x+1].etat != 'actif' or liste[y-1][x].etat != 'actif' or liste[y-1][x-1].etat != 'actif' or liste[y-1][x+1].etat != 'actif' or liste[y+1][x].etat != 'actif' or liste[y+1][x-1].etat != 'actif' or liste[y+1][x+1].etat != 'actif':
                y = random.randint(1,len(liste)-2)
                x = random.randint(1,len(liste[0])-2)
            listetemp[0].X = 50+50*x
            listetemp[0].Y = 50+50*y
            liste[y][x] = listetemp.pop(0)
    
    elif gen == 0:
        while listetemp != []:
            y = random.randint(1,len(liste)-2)
            x = random.randint(1,len(liste[0])-2)
            while liste[y][x].etat != 'actif' :
                y = random.randint(1,len(liste)-2)
                x = random.randint(1,len(liste[0])-2)
            listetemp[0].X = 50+50*x
            listetemp[0].Y = 50+50*y
            liste[y][x] = listetemp.pop(0)
    
    for y in range(0,L):
        for x in range(1,C):
            liste[y][x].changevoisins(liste[y][x-1],None,None,None)
    for y in range(1,L):
        for x in range(0,C):
            liste[y][x].changevoisins(None,liste[y-1][x],None,None)
    
    return liste[0][0]
#-----
class perso:
    def __init__(self,emplacement):
        self.emp = emplacement
        self.empD = None
        self.circuit = False
        self.pauseDepl = 0
        self.action = 0
        self.fin = 0
        self.sens = 10
        self.pauseSens = 0
    
    def actionP(self):
        self.deplacement()
        self.sensibilite()
    
    def deplacement(self):
        if keys[pygame.K_SPACE] and self.emp.etat == 'debut' and self.circuit == False and self.emp.LS == []:
            self.circuit = True
            self.empD = self.emp
            self.empD.LS.append(self.emp)
        if keys[pygame.K_BACKSPACE] and self.emp.etat == 'debut' and self.emp.LS != []:
            if self.emp.fini == True:
                self.fin -= 1
                self.emp.fini = False
            if self.empD == self.emp:
                self.empD = None
                self.circuit = False
            self.emp.LS = []
            self.emp.retire()
         
        if self.empD != None and self.emp.etat == 'fin' and self.emp in self.empD.LS:
            self.empD.fini = True
            self.empD = None
            self.circuit = False
            self.fin += 1
        
        if self.pauseDepl == 0:
            if keys[pygame.K_q] and self.emp.G != None:
                self.action = 1
                if self.circuit == True and (self.emp.G.etat == 'actif' or (self.emp.G.etat == 'fin' and self.emp.G.val == self.empD.val)) and self.empD.LS[len(self.empD.LS)-1].G == self.emp.G and (self.emp.G.S == '' or self.emp.G in self.empD.LS):
                    if self.emp.G not in self.empD.LS :
                        self.empD.LS.append(self.emp.G)
                    else:
                        self.empD.LS = self.empD.LS[:self.empD.LS.index(self.emp.G)+1]
                self.emp = self.emp.G
                self.pauseDepl = self.sens
        
            elif keys[pygame.K_z] and self.emp.H != None:
                self.action = 1
                if self.circuit == True and (self.emp.H.etat == 'actif' or (self.emp.H.etat == 'fin' and self.emp.H.val == self.empD.val) ) and self.empD.LS[len(self.empD.LS)-1].H == self.emp.H and (self.emp.H.S == '' or self.emp.H in self.empD.LS):
                    if self.emp.H not in self.empD.LS :
                        self.empD.LS.append(self.emp.H)
                    else:
                        self.empD.LS = self.empD.LS[:self.empD.LS.index(self.emp.H)+1]
                self.emp = self.emp.H
                self.pauseDepl = self.sens
        
            elif keys[pygame.K_d] and self.emp.D != None:
                self.action = 1
                if self.circuit == True and (self.emp.D.etat == 'actif' or (self.emp.D.etat == 'fin' and self.emp.D.val == self.empD.val) ) and self.empD.LS[len(self.empD.LS)-1].D == self.emp.D and (self.emp.D.S == '' or self.emp.D in self.empD.LS):
                    if self.emp.D not in self.empD.LS :
                        self.empD.LS.append(self.emp.D)
                    else:
                        self.empD.LS = self.empD.LS[:self.empD.LS.index(self.emp.D)+1]
                self.emp = self.emp.D
                self.pauseDepl = self.sens
        
            elif keys[pygame.K_s] and self.emp.B != None:
                self.action = 1
                if self.circuit == True and (self.emp.B.etat == 'actif' or (self.emp.B.etat == 'fin' and self.emp.B.val == self.empD.val) ) and self.empD.LS[len(self.empD.LS)-1].B == self.emp.B and (self.emp.B.S == '' or self.emp.B in self.empD.LS):
                    if self.emp.B not in self.empD.LS :
                        self.empD.LS.append(self.emp.B)
                    else:
                        self.empD.LS = self.empD.LS[:self.empD.LS.index(self.emp.B)+1]
                self.emp = self.emp.B
                self.pauseDepl = self.sens
        else:
            self.pauseDepl -= 1
    
    def sensibilite(self):
        if self.pauseSens == 0:
            if keys[pygame.K_UP] and self.sens > 1:
                self.sens -= 1
                self.pauseSens = 5
            if keys[pygame.K_DOWN] and self.sens < 10:
                self.sens += 1
                self.pauseSens = 5
        else:
            self.pauseSens -= 1
#-----
class bouton:
    def __init__(self,posX,posY,taille,texte):
        self._posX = posX
        self._posY = posY
        self._taille = taille
        self._pause = 0
        self._texte = texte
        
    def collision(self):
        collision = False
        pos = pygame.mouse.get_pos()
        if pos[0] > self._posX and pos[0] < self._posX+self._taille:
            if pos[1] > self._posY and pos[1] < self._posY+self._taille:
                collision = True
        return collision
    
    def action(self):
        if self._pause <= 0:
            if self.collision() and pygame.mouse.get_pressed(3)[0]:
                self._pause = 10
                return True
        self._pause -= 1
    
    def affiche(self):
        pygame.draw.rect(screen,(255,255,255),pygame.Rect(self._posX,self._posY,self._taille,self._taille))
        screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(self._texte,False,(0,0,0)),(self._posX+self._taille//3,self._posY))
#-----

listeb = [bouton(1090,40,50,'L↑'),bouton(1090,160,50,'L↓'),bouton(1090,100,50,'0'),bouton(1210,100,50,'1'),bouton(1210,40,50,'C↑'),bouton(1210,160,50,'C↓'),bouton(1150,100,50,'✓'),bouton(1150,40,50,'+'),bouton(1150,160,50,'-')]

Ecran = 1
L = 13
C = 13
G = 1
NBC = 10
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
    
    if Ecran == 1:
        for l in range(L):
            for c in range(C):
                pygame.draw.rect(screen,(125,125,125),pygame.Rect(50+50*c,50+50*l,30,30))
        
        for bouton in listeb:
            bouton.affiche()
        
        screen.blit(pygame.font.SysFont('segoeuisymbol',20).render(f'Type de generation : {G}',False,(255,255,255)),(1075,225))
        if G == 0:
            screen.blit(pygame.font.SysFont('segoeuisymbol',20).render(f'⚠Gen0 souvent impossible⚠',False,(255,255,255)),(1025,250))
        screen.blit(pygame.font.SysFont('segoeuisymbol',20).render(f'Nb lignes : {L}',False,(255,255,255)),(1075,275))
        screen.blit(pygame.font.SysFont('segoeuisymbol',20).render(f'Nb colonnes : {C}',False,(255,255,255)),(1075,300))
        screen.blit(pygame.font.SysFont('segoeuisymbol',20).render(f'Nb de boitiers : {NBC}',False,(255,255,255)),(1075,325))
        
        if listeb[0].action():
            if L < 19:
                L += 1
        if listeb[1].action():
            if G == 0 and L > 3 and (L-1)*C >= NBC*8:
                L -= 1
            elif G == 1 and L > 3 and (L-1)*C >= NBC*16:
                L -= 1
        
        if listeb[2].action():
            G = 0
        if listeb[3].action() and L*C >= NBC*16:
            G = 1
        
        if listeb[4].action():
            if C < 24:
                C += 1
        if listeb[5].action():
            if G == 0 and C > 3 and L*(C-1) >= NBC*8:
                C -= 1
            elif G == 1 and C > 3 and L*(C-1) >= NBC*16:
                C -= 1
        
        if listeb[7].action():
            if G == 0 and NBC < 10 and L*C >= (NBC+1)*8:
                NBC += 1
            elif G == 1 and NBC < 10 and L*C >= (NBC+1)*16:
                NBC += 1
        if listeb[8].action():
            if NBC > 1:
                NBC -= 1
        
        if listeb[6].action():
            A1 = generation(L,C,G,NBC)
            P1 = perso(A1)
            Ecran = 2
    
    elif Ecran == 2:
        A1.actionC(P1.emp)
        P1.actionP()
        screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Sensibilité : {11-P1.sens}',False,(255,255,255)),(1075,5))
        screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Case : {P1.emp.etat}',False,(255,255,255)),(50,5))
        screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Boitiers reliés : {P1.fin}',False,(255,255,255)),(250,5))
        if NBC == P1.fin :
            Ecran = 3
    
    elif Ecran == 3:
        screen.blit(pygame.font.SysFont('segoeuisymbol',50).render('Félicitations ! Vous avez relié tous les boitiers !',False,(255,255,255)),(175,ECR[1]//2))
    # flip() the display to put your work on screen
    pygame.display.flip()

    # limits FPS to 60
    # dt is delta time in seconds since last frame, used for framerate-
    # independent physics.
    dt = clock.tick(60) / 1000

pygame.quit()