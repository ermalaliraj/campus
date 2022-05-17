import React from 'react'

interface Props {
	style?: React.CSSProperties
	children?: any
	h1?: boolean
	h2?: boolean
	bold?: boolean
	black?: boolean
	inline?: boolean
	className?: string
}

const Text: React.FC<Props> = ({style = {}, children = '', h1, h2, bold, inline, className, black}) => {
	const styled = {} as React.CSSProperties
	if (bold) styled.fontWeight = 'bold'
	if (inline) styled.display = 'inline'
	if (black) styled.color = 'black'

	if (h1) return <h1 style={{...styled, ...style}}>{children}</h1>
	if (h2) return <h2 style={{...styled, ...style}}>{children}</h2>
	return (
		<span style={{...styled, ...style}} className={className}>
      {children}
    </span>
	)
}

export default Text
