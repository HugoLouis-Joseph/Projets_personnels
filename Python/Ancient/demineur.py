import pygame
import random
ECR = (1280,720)
#----- pygame setup
pygame.init()
screen = pygame.display.set_mode(ECR)
clock = pygame.time.Clock()
running = True
dt = 0
player_pos = pygame.Vector2(screen.get_width() / 2, screen.get_height() / 2)
#-----
class case:
    def __init__(self,x,y,t,col=(125,125,125)):
        self.X = x
        self.Y = y
        self.T = t
        self.col = col
        
        #Cases suivantes dans les directions cardinales dans cet ordre : gauche , haut , doite , bas
        self.g = None
        self.h = None
        self.d = None
        self.b = None
        
        self.aff = False
        
        self.estbombe = False
        self.bombep = 0
    
    def changevoisins(self,g,h,d,b):
        #Modifie les voisins d'une case , permettant le déplacement du joueur
        if g != None:
            g.d = self
            self.g = g
        if h != None:
            h.b = self
            self.h = h
        if d != None:
            d.g = self
            self.d = d
        if b != None:
            b.h = self
            self.b = b
    
    def bombe(self):
        self.estbombe = True
        #regarde les cases avoisinantes si présentes et leur incrémente leur attribut bombep
        if self.g != None:
            self.g.bombep += 1
            if self.h != None:
                self.g.h.bombep += 1
            if self.b != None:
                self.g.b.bombep += 1
        if self.d != None:
            self.d.bombep += 1
            if self.h != None:
                self.d.h.bombep += 1
            if self.b != None:
                self.d.b.bombep += 1
        if self.h != None:
            self.h.bombep += 1
        if self.b != None:
            self.b.bombep += 1
    
    def devoile(self,perso):
        #Dévoile toutes les cases 0 plus celles les avoisinant d'une "section"
        self.aff = True
        perso.casevis += 1
        if self.bombep == 0 and not self.estbombe:
            if self.g != None and not self.g.aff :
                self.g.devoile(perso)
            if self.h != None and not self.h.aff :
                self.h.devoile(perso)
            if self.d != None and not self.d.aff :
                self.d.devoile(perso)
            if self.b != None and not self.b.aff :
                self.b.devoile(perso)
    
    def affiche(self):
        pygame.draw.rect(screen,self.col,pygame.Rect(self.X,self.Y,self.T,self.T),0)
        #True si la case a été révélée par le joueur
        if self.aff:
            if not self.estbombe:
                screen.blit(pygame.font.SysFont('segoeuisymbol',self.T//2).render(f'{self.bombep}',False,(255,255,255)),(self.X+self.T//3,self.Y+self.T//6))
            else:
                pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.X+self.T//4,self.Y+self.T//4,self.T//2,self.T//2),0)
class perso:
    def __init__(self,emp,casemax):
        #case sur laquelle le joueur se trouve
        self.emp = emp
        #temps de pause entre les actions
        self.p = 0
        #état de la victoire , None = aucun , False = perdu , True = gagné
        self.vic = None
        #cases correctement marquées comme bombe
        self.compteur = 0
        #nombre de cases visitées
        self.casevis = 0
        #nombre de cases à visiter au total
        self.casemax = casemax
    
    def affiche(self):
        pygame.draw.rect(screen,(255,255,255),pygame.Rect(self.emp.X,self.emp.Y,self.emp.T,self.emp.T),3)
    
    def dep(self):
        #vérifie qu'une case est présente dans la direction vers laquelle le joueur souhaite se déplacer
        if self.p == 0:
            if (keys[pygame.K_q] or keys[pygame.K_LEFT]) and self.emp.g != None:
                self.emp.affiche()
                self.emp = self.emp.g
                self.p = 10
            elif (keys[pygame.K_z] or keys[pygame.K_UP]) and self.emp.h != None:
                self.emp.affiche()
                self.emp = self.emp.h
                self.p = 10
            elif (keys[pygame.K_d] or keys[pygame.K_RIGHT]) and self.emp.d != None:
                self.emp.affiche()
                self.emp = self.emp.d
                self.p = 10
            elif (keys[pygame.K_s] or keys[pygame.K_DOWN]) and self.emp.b != None:
                self.emp.affiche()
                self.emp = self.emp.b
                self.p = 10
        else:
            self.p -= 1
    
    def joue(self):
        #cet attribut sert de premier temps de pause lors de la création du plateau afin d'éviter une action non voulue
        if self.p2 == 0:
            r = False
            #dévoile la case sur laquelle le personnage se trouve
            if keys[pygame.K_SPACE] and not self.emp.aff and not self.emp.col == (255,0,0):
                r = True
                self.emp.devoile(self)
                if self.emp.estbombe:
                    self.vic = False
            #marque comme bombe la case sur laquelle le personnage se trouve si elle ne l'est pas déjà
            if pygame.mouse.get_pressed()[0] and not self.emp.aff and not self.emp.col == (255,0,0):
                r = True
                #ajoute 1 au compteur si la case est une bombe , sinon enlève 1
                if self.emp.estbombe:
                    self.compteur += 1
                else:
                    self.compteur -= 1
                self.emp.col = (255,0,0)
            #enlève la marque de la case sur laquelle le personnage se trouve si elle est présente
            elif pygame.mouse.get_pressed()[2] and not self.emp.aff and self.emp.col == (255,0,0):
                r = True
                #enlève 1 au compteur si la case est une bombe , sinon ajoute 1
                if self.emp.estbombe:
                    self.compteur -= 1
                else:
                    self.compteur += 1
                self.emp.col = (125,125,125)
            
            #si toutes les bombes sont marquées et que toutes les autres cases sont révélées , le joueur gagne
            if self.compteur == nbbombe and self.casevis == self.casemax:
                self.vic = True
            return r
        else:
            self.p2 -= 1

class bouton:
    def __init__(self,posX,posY,taille,texte):
        self.posX = posX
        self.posY = posY
        self.taille = taille
        self.pause = 0
        self.texte = texte
        
    def collision(self):
        #Détermine si le curseur est en contact avec le bouton
        collision = False
        pos = pygame.mouse.get_pos()
        if pos[0] > self.posX and pos[0] < self.posX+self.taille:
            if pos[1] > self.posY and pos[1] < self.posY+self.taille:
                collision = True
        return collision
    
    def action(self):
        #Détermine si le joueur appuie sur le clic de souris gauche
        if self.pause <= 0:
            if self.collision() and pygame.mouse.get_pressed(3)[0]:
                self.pause = 10
                return True
        self.pause -= 1
    
    def affiche(self):
        pygame.draw.rect(screen,(125,125,125),pygame.Rect(self.posX,self.posY,self.taille,self.taille))
        screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(self.texte,False,(0,0,0)),(self.posX+self.taille//3,self.posY+self.taille//3))
#-----

#Initialisation du plateau
liste = []
#Taille des cases
t = 50
#Nombre de lignes initial
L = 1
#Nombre de colonnes initial
C = 1
#Permet de continuer la partie (ne pas mettre la valeur à 1)
k = 0
#Nombre de bombes initial
nbbombe = 1
#Premier affichage du plateau
affprem = True
#Début du jeu
jeu = False
#Gestion de l'aide
aide = False
#Espace entre les cases
esp = 100
#Utilisation d'un bouton
modif = False
#Création de la liste de boutons
listeb = [bouton(ECR[0]-75,25,50,'L+'),bouton(ECR[0]-150,25,50,'L-'),bouton(ECR[0]-75,100,50,'C+'),bouton(ECR[0]-150,100,50,'C-'),bouton(ECR[0]-75,175,50,'T+'),bouton(ECR[0]-150,175,50,'T-'),bouton(ECR[0]-75,250,50,'E+'),bouton(ECR[0]-150,250,50,'E-'),bouton(ECR[0]-75,325,50,'B+'),bouton(ECR[0]-150,325,50,'B-'),bouton(ECR[0]-75,ECR[1]-75,50,'V'),bouton(ECR[0]-150,ECR[1]-75,50,'A')]
#-----
while running:
    # poll for events
    # pygame.QUIT event means the user clicked X to close your window
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # fill the screen with a color to wipe away anything from last frame
    keys = pygame.key.get_pressed()
    
    if jeu:
        #premier affichage de toutes les cases
        if affprem:
            affprem = False
            for l in liste:
                for c in l:
                    c.affiche()
        #k est une variable permettetant de continuer la partie tant que le joueur n'a ni gagné , ni perdu
        if k != 1:
            perso.affiche()
            #Idem pour l'attribut vic de perso
            if perso.vic == None:
                perso.dep()
                r = perso.joue()
                #réaffichage des cases une fois que le joueur effectue une action , afin de diminuer les coûts
                if r :
                    screen.fill("black")
                    for l in liste:
                        for c in l:
                            c.affiche()
            elif not perso.vic :
                print('Défaite')
                k = 1
            else:
                print('Victoire')
                k = 1
    else:
        #pré-affichage de la génération des cases
        if modif or affprem:
            screen.fill("black")
            for x in range(L):
                for y in range(C):
                    pygame.draw.rect(screen,(255,255,255),pygame.Rect(10+esp*y,10+esp*x,t,t))
            pygame.draw.rect(screen,(125,125,125),pygame.Rect(ECR[0]-150,400,125,225))
            screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Cases : {L*C}',False,(255,125,125)),(ECR[0]-150,400))
            screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Taille : {t}',False,(255,125,125)),(ECR[0]-150,450))
            screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Espace : {esp}',False,(255,125,125)),(ECR[0]-150,500))
            screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Bombes : {nbbombe}',False,(255,125,125)),(ECR[0]-150,550))
            screen.blit(pygame.font.SysFont('segoeuisymbol',30).render(f'Aide : {aide}',False,(255,125,125)),(ECR[0]-150,600))
            modif = False
            affprem = False
        #contrôle de l'activation des boutons
        for b in listeb:
            b.affiche()
        if listeb[0].action():
            L += 1
            modif = True
        if listeb[1].action() and L > 1:
            L -= 1
            modif = True
        if listeb[2].action():
            C += 1
            modif = True
        if listeb[3].action() and C > 1:
            C -= 1
            modif = True
        if listeb[4].action():
            t += 5
            modif = True
        if listeb[5].action():
            t -= 5
            modif = True
        if listeb[6].action():
            esp += 5
            modif = True
        if listeb[7].action():
            esp -= 5
            modif = True
        if listeb[8].action() and nbbombe < L*C:
            nbbombe += 1
            modif = True
        if listeb[9].action() and nbbombe > 1:
            nbbombe -= 1
            modif = True
        #ce bouton correspond au démarrage du jeu , et donc à la création de toutes les cases du plateau
        if listeb[10].action():
            jeu = True
            for _ in range(L):
                liste.append([])

            for y in range(L):
                for x in range(C):
                    liste[y].append(case(10+esp*(x),10+esp*(y),t))

            for y in range(0,L):
                for x in range(1,C):
                    liste[y][x].changevoisins(liste[y][x-1],None,None,None)
            for y in range(1,L):
                for x in range(0,C):
                    liste[y][x].changevoisins(None,liste[y-1][x],None,None)
            #placement des bombes
            for _ in range(nbbombe):
                x = random.randint(0,len(liste)-1)
                y = random.randint(0,len(liste[0])-1)
                while liste[x][y].estbombe:
                    x = random.randint(0,len(liste)-1)
                    y = random.randint(0,len(liste[0])-1)
                liste[x][y].bombe()
            #création du personnage (joueur)
            perso = perso(liste[0][0],L*C-nbbombe)
            perso.p2 = 60
            affprem = True
            screen.fill("black")
            #si aide vaut True , la première case ayant 0 pour nombre de bombes (attribut bombep) est révélée
            if aide:
                cas = False
                for l in liste:
                    for case in l:
                        if case.bombep == 0 and not case.estbombe and not cas:
                            case.devoile(perso)
                            cas = True
                
        if listeb[11].action():
            if not aide:
                aide = True
            else:
                aide = False
            modif = True
    # flip() the display to put your work on screen
    pygame.display.flip()

    # limits FPS to 60
    # dt is delta time in seconds since last frame, used for framerate-
    # independent physics.
    dt = clock.tick(60) / 1000

pygame.quit()