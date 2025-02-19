
;            Exercice de Vagues concentriques
;            --------------------------------




;    VARIABLES

velocite%=10            ;    augmenter pour un mvt plus rapide
segmentsX=60        ;    nb de segments sur X
segmentsZ=60        ;    nb de segments sur Z
amplitude=40        ;    coefficient multiplicateur du vecteur (amplitude des vagues)


;    Definition de l ecran

Graphics3D 800,600,32,2
SetBuffer BackBuffer()
HidePointer

;WireFrame 1

AmbientLight 0,0,0

light1=CreateLight(2)
PositionEntity light1,-30,30,30
LightColor light1,200,150,50
LightRange light1,100

light2=CreateLight(2)
PositionEntity light2,150,30,-150
LightColor light2,50,150,200
LightRange light2,100

;    Definition des objets

camera=CreateCamera()
PositionEntity camera,segmentsX/2,0,-segmentsZ



mesh=CreateMesh()
superficie=CreateSurface(mesh)
For coordX=0 To segmentsX
    For coordZ=0 To segmentsZ
        AddVertex superficie,coordX,0,coordZ        
    Next
Next

For coordX=0 To segmentsX-1
    For coordZ=0 To segmentsZ-1
        origine=coordZ+(coordX*(segmentsX+1))
        contigu=origine+1
        oppose=origine+segmentsX+2
        inferieur=oppose-1
        AddTriangle superficie,origine,oppose,contigu
        AddTriangle superficie,origine,inferieur,oppose
    Next
Next

PositionEntity mesh,0,-10,0
RotateEntity mesh,180,0,0
UpdateNormals mesh
EntityShininess mesh,0.1

;    Boucle principale

Repeat

    ; mouvement de la souris
    mx#=mx#-Float(MouseYSpeed())/-7 : mx=mx/1.5
    my#=my#+Float(MouseXSpeed())/-7 : my=my/1.5
    x#=EntityPitch(camera)+mx
    y#=EntityYaw(camera)+my
    If x>=89 And x<=180 x=89
    If x<=-89 And x>=-180 x=-89
    RotateEntity camera,0,y,0
    TurnEntity camera,x,0,0
    MoveMouse 320,240
    
    
    ; gestion des fleches
    If KeyDown(200) MoveEntity camera,0,0,0.5
    If KeyDown(208) MoveEntity camera,0,0,-0.5
    If KeyDown(203) MoveEntity camera,-0.5,0,0
    If KeyDown(205) MoveEntity camera,0.5,0,0
    
    If KeyDown(78) amplitude=amplitude+1
    If KeyDown(74) amplitude=amplitude-1
    
    ptcentral=Int(segmentsX*segmentsZ/2)    ; récuperation du point central du mesh
    vxref#=VertexX(superficie,ptcentral)    ; et de ses
    vzref#=VertexZ(superficie,ptcentral)    ; coordonnées
    
    
    
    mouvement#=MilliSecs()/velocite            ; rafraichissement

	For n=0 To CountVertices(superficie)-1												; on passe en revue chaque vertex

		vecteur=Sqr(((vx#-vxref#)*(vx#-vxref#))+((vz#-vzref#)*(vz#-vzref#)))*amplitude	; calcul du vecteur centre|vertex

		vx#=VertexX(superficie,n)

		vy#=Cos(mouvement+vecteur)

		vz#=VertexZ(superficie,n)

		VertexCoords superficie,n,vx#,vy#,vz#

	Next
    
  UpdateNormals mesh

 RenderWorld

 Text 10,10,"+ ou - .... Modifier l' amplitude des vagues"

 Flip

Until KeyDown(1)
;~IDEal Editor Parameters:
;~C#Blitz3D